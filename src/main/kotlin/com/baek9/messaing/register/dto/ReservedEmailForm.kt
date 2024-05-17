package com.baek9.messaing.register.dto

import com.baek9.messaing.register.entity.ReservedPushRegister
import lombok.Getter
import java.time.LocalDateTime

@Getter
class ReservedEmailForm(
    title: String,
    message: String,
    email : String,
    val atTime: LocalDateTime
) : EmailForm(title, message, email) {

    fun toEntity() : ReservedPushRegister {
        return ReservedPushRegister(email=email, message=message, title=title, atTime=atTime)
    }
}