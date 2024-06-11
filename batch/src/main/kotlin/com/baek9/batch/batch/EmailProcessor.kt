package com.baek9.batch.batch

import com.baek9.domain.email.EmailForm
import com.baek9.domain.register.ReservedPushRegister
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import java.util.concurrent.CompletableFuture

class EmailProcessor(
    val kafkaTemplate: KafkaTemplate<String, EmailForm>
) : ItemProcessor<ReservedPushRegister, ReservedPushRegister>{
    val log = LoggerFactory.getLogger(EmailProcessor::class.java)

    override fun process(item: ReservedPushRegister): ReservedPushRegister {
        val future: CompletableFuture<SendResult<String, EmailForm>> =
            kafkaTemplate.send("alert", "reserved", EmailForm.from(item))
        future.join()

        log.info("processing {}", item.toString())

        item.commit()
        return item
    }
}