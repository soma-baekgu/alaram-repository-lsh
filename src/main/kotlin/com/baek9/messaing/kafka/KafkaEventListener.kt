package com.baek9.messaing.kafka

import com.baek9.messaing.register.dto.EmailForm
import com.baek9.messaing.email.EmailSender
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class KafkaEventListener (
    val emailSender: EmailSender
) {
    val log = LoggerFactory.getLogger(KafkaEventListener::class.java)

    @KafkaListener(groupId = "group001", topics = ["urgent", "reserved"])
    fun urgentConsumer1(
        record: ConsumerRecord<String, EmailForm>,
        ack: Acknowledgment,
        consumer: Consumer<String, EmailForm>
    ) {
        log.info("{}", record.value())

        emailSender.send(record.value().email, record.value().title, record.value().message)
        ack.acknowledge()
    }

    @KafkaListener(groupId = "group001", topics = ["urgent", "reserved"])
    fun urgentConsumer2(
        record: ConsumerRecord<String, EmailForm>,
        ack: Acknowledgment,
        consumer: Consumer<String, EmailForm>
    ) {
        log.info("{}", record.value())

        emailSender.send(record.value().email, record.value().title, record.value().message)
        ack.acknowledge()
    }

}