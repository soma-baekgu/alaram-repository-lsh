package com.baek9.messaing.email

import lombok.Getter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.Properties

@Getter
@Configuration
class MailSenderConfig {

    @Value("\${spring.mail.host}")
    lateinit var host: String

    @Value("\${spring.mail.port}")
    lateinit var port: String

    @Value("\${spring.mail.username}")
    lateinit var username: String

    @Value("\${spring.mail.password}")
    lateinit var password: String

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    lateinit var auth: String

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    lateinit var starttls: String

    @Bean
    fun javaMailSender() : JavaMailSender {
        val javaMailSender: JavaMailSenderImpl = JavaMailSenderImpl()

        javaMailSender.host = host
        javaMailSender.port = port.toInt()
        javaMailSender.username = username
        javaMailSender.password = password

        val props: Properties = javaMailSender.javaMailProperties
        props["mail.smtp.starttls.enable"] = starttls.toBoolean()
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = auth.toBoolean()
        props["mail.smtp.starttls.enable"] = starttls.toBoolean()


        return javaMailSender
    }
}