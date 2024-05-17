package com.baek9.messaing.register.dto

import lombok.Getter

@Getter
open class EmailForm(
    val title: String,
    val message: String,
    val email : String
) {
    companion object {
        val ERROR: EmailForm = EmailForm("", "", "dltmd202@gmail.com")
    }

    override fun toString(): String {
        return "{ \"title\": \"$title\", \"message\": \"$message\", \"email\": \"$email\" }"
    }
}
