package com.baek9.messaing.register

import com.baek9.messaing.register.dto.EmailForm
import com.baek9.messaing.register.dto.ReservedEmailForm
import com.baek9.messaing.register.entity.ReservedPushRegister
import jakarta.transaction.Transactional
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PushRegisterService(
    val kafkaTemplate: KafkaTemplate<String, String>,
    val reservedPushRegisterRepository: PushRegisterRepository
) {
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