package com.baek9.core.register.dto

import com.baek9.domain.register.ReservedPushRegister
import lombok.Getter

@Getter
open class EmailForm(
    val title: String,
    val message: String,
    val email : String
) {
    companion object {
        val ERROR: EmailForm = EmailForm("", "", "dltmd202@gmail.com")

        fun from(entity: ReservedPushRegister): EmailForm {
            return EmailForm(entity.title, entity.message, entity.email)
        }
    }

    override fun toString(): String {
        return "{ \"title\": \"$title\", \"message\": \"$message\", \"email\": \"$email\" }"
    }

}
