package com.baek9.messaing.register

import com.baek9.messaing.register.dto.EmailForm
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/alert")
class PushRegistrationController(
    val pushRegisterService: PushRegisterService
) {
    val log = LoggerFactory.getLogger(PushRegistrationController::class.java)

    @PostMapping("/urgent")
    fun register(@RequestBody emailForm: EmailForm) : ResponseEntity<Boolean> {
        log.info("push alert to {}", emailForm.email)
        return ResponseEntity.ok(pushRegisterService.sendUrgently(emailForm))
    }
}