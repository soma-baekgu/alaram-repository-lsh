package com.baek9.api.register

import com.baek9.domain.register.ReservedPushRegister
import com.baek9.api.register.dto.ReservedEmailForm
import com.baek9.domain.email.EmailForm
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PushRegisterService(
    val kafkaTemplate: KafkaTemplate<String, EmailForm>,
    val reservedPushRegisterRepository: PushRegisterRepository
) {
    val log = LoggerFactory.getLogger(PushRegisterService::class.java)

    fun sendUrgently(emailForm: EmailForm) : Boolean {
        kafkaTemplate.send("alert", "urgent", emailForm)
        return true
    }

    @Transactional
    fun reservePushAlert(reservedEmailForm: ReservedEmailForm): Boolean? {
        val reservedPush: ReservedPushRegister = reservedPushRegisterRepository.save(reservedEmailForm.toEntity())
        return true
    }
}