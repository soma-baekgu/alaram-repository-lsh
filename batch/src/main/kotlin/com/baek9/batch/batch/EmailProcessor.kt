package com.baek9.batch.batch

import com.baek9.domain.register.ReservedPushRegister
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.kafka.core.KafkaTemplate

class EmailProcessor(
    val kafkaTemplate: KafkaTemplate<String, String>
) : ItemProcessor<ReservedPushRegister, ReservedPushRegister>{
    val log = LoggerFactory.getLogger(EmailProcessor::class.java)

    override fun process(item: ReservedPushRegister): ReservedPushRegister {
        kafkaTemplate.send("reserved", item.toString())
        log.info("processing {}", item)
        log.info("processing {}", item.toString())
        item.commit()
        return item
    }
}