package com.baek9.batch.batch

import com.baek9.domain.register.ReservedPushRegister
import jakarta.persistence.EntityManagerFactory
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.orm.jpa.JpaTransactionManager
import java.time.LocalDateTime


@Configuration
class ReservedPushRegisterJobConfiguration(
    val jobRepository: JobRepository,
    val entityManagerFactory: EntityManagerFactory,
    val transactionManger: JpaTransactionManager,
    val kafkaTemplate: KafkaTemplate<String, String>
) {
    val log = LoggerFactory.getLogger(ReservedPushRegisterJobConfiguration::class.java)

    companion object {
        const val CHUNK_SIZE = 10
    }

    @Bean
    @StepScope
    fun reader(): JpaCursorItemReader<ReservedPushRegister> {
        // TODO : 변수화
        val now = LocalDateTime.now()
        log.info("now {}", now)

        return JpaCursorItemReaderBuilder<ReservedPushRegister>()
            .name("reader")
            .entityManagerFactory(entityManagerFactory)
            .queryString(
                "select r " +
                    "from ReservedPushRegister r " +
                    "where r.atTime < :current and r.atTime >= :prev " +
                    "and r.commited = false"
            )
            .parameterValues(
                mapOf("current" to now,
                    "prev" to now.minusMinutes(5))
            )
            .build()
    }

    @Bean
    fun processor(): EmailProcessor {
        return EmailProcessor(kafkaTemplate)
    }

    @Bean
    fun writer(): JpaItemWriter<ReservedPushRegister> {
        val itemWriter: JpaItemWriter<ReservedPushRegister> = JpaItemWriter()
        itemWriter.setEntityManagerFactory(entityManagerFactory)
        return itemWriter
    }

    @Bean
    fun step(): Step {
        return StepBuilder("step", jobRepository)
            .chunk<ReservedPushRegister, ReservedPushRegister>(CHUNK_SIZE, transactionManger)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build()
    }

    @Bean
    fun job() : Job {
        return JobBuilder("reservedUserEnqueueJob", jobRepository)
            .start(step())
            .listener(JobListener())
            .build()
    }
}