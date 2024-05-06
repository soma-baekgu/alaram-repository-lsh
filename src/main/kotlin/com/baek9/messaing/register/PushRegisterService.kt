package com.baek9.messaing.register

import com.baek9.messaing.register.dto.EmailForm
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PushRegisterService(
    val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun sendUrgently(emailForm: EmailForm) : Boolean {
        kafkaTemplate.send("urgent", emailForm.toString())
        return true
    }
}