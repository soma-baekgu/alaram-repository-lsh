package com.baek9.core.register

import com.baek9.domain.register.ReservedPushRegister
import com.baek9.core.kafka.KafkaEventListener
import com.baek9.core.register.dto.ReservedEmailForm
import com.baek9.domain.email.EmailForm
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

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
}