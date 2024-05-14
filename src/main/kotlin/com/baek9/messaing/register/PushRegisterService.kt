package com.baek9.messaing.register

import com.baek9.messaing.kafka.KafkaEventListener
import com.baek9.messaing.register.dto.EmailForm
import com.baek9.messaing.register.dto.ReservedEmailForm
import com.baek9.messaing.register.entity.ReservedPushRegister
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PushRegisterService(
    val kafkaTemplate: KafkaTemplate<String, String>,
    val reservedPushRegisterRepository: PushRegisterRepository
) {
    val log = LoggerFactory.getLogger(KafkaEventListener::class.java)

    fun sendUrgently(emailForm: EmailForm) : Boolean {
        kafkaTemplate.send("urgent", emailForm.toString())
        return true
    }

    @Transactional
    fun reservePushAlert(reservedEmailForm: ReservedEmailForm): Boolean? {
        val reservedPush: ReservedPushRegister = reservedPushRegisterRepository.save(reservedEmailForm.toEntity())
        return true
    }

    @Transactional
    @Scheduled(fixedDelay = 300_000)
    fun enqueue(){
        val periodReservation: List<ReservedPushRegister> =
            reservedPushRegisterRepository.getNotCommitedPeriodReservation(
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5))

        periodReservation
            .forEach{ r ->
                log.info("record {}", r)
                kafkaTemplate.send("reserved", r.toEmailForm().toString())
                r.commit()
            }


        log.info("records {}", periodReservation)
    }
}