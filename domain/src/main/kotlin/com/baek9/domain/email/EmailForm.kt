package com.baek9.domain.email

import com.baek9.domain.register.ReservedPushRegister

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
