package com.baek9.messaing.email

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailSender (
    val mailSender : JavaMailSender,
    val emailConfig: MailSenderConfig
) {

   fun send(email: String, title : String, message: String) {
       val mailMessage: SimpleMailMessage = SimpleMailMessage()
       mailMessage.from = emailConfig.username
       mailMessage.setTo(email)
       mailMessage.subject = title
       mailMessage.text = message

       mailSender.send(mailMessage)
   }
}