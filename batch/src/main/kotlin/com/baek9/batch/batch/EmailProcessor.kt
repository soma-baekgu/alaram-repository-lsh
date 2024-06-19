package com.baek9.batch.batch

import com.baek9.domain.email.EmailForm
import com.baek9.domain.register.ReservedPushRegister
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException

class EmailProcessor(
    val kafkaTemplate: KafkaTemplate<String, EmailForm>
) : ItemProcessor<ReservedPushRegister, ReservedPushRegister>{
    val log = LoggerFactory.getLogger(EmailProcessor::class.java)

    override fun process(item: ReservedPushRegister): ReservedPushRegister {
        val future: CompletableFuture<SendResult<String, EmailForm>> =
            kafkaTemplate.send("alert", "reserved", EmailForm.from(item))

        try{
            future.join()
            log.info("processing {}", item.toString())
            // TODO 이렇게 하는게 적합한지 모르겠음
            item.commit()
        } catch (e: CompletionException){
            log.error("Failed reservedPushRegister id={}", item.id)
        }

        return item
    }
}