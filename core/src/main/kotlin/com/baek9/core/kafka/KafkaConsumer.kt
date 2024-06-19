package com.baek9.core.kafka

import com.baek9.core.email.EmailSender
import com.baek9.domain.email.EmailForm
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class KafkaConsumer (
    val emailSender: EmailSender
) {
    val log = LoggerFactory.getLogger(KafkaConsumer::class.java)

    // TODO concurrency 설정하는 거 의도한 대로 파티셔닝된건지 확인해오기
    @KafkaListener(groupId = "group002", topics = ["alert"], concurrency = "9")
    fun alertConsumer(
        record: ConsumerRecord<String, EmailForm>,
        ack: Acknowledgment,
        consumer: Consumer<String, EmailForm>
    ) {
        log.info("key={}, value={}", record.key(), record.value())

        CompletableFuture.runAsync {
            try {
                emailSender.send(record.value().email, record.value().title, record.value().message)
                ack.acknowledge()
            } catch (e: Exception) {
                log.error("error", e)
            } finally {
                consumer.commitSync()
            }
        }

        // TODO ack 설정 값 확인하고 acknowledge 동작 어떻게 하는지 확인하기(인터셉터에서 특정 메시지 로깅)
    }

}