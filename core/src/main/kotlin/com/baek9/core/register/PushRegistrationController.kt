package com.baek9.core.register

import com.baek9.core.register.dto.EmailForm
import com.baek9.core.register.dto.ReservedEmailForm
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

    @PostMapping("/reserved")
    fun registerReserved(@RequestBody reservedEmailForm: ReservedEmailForm) : ResponseEntity<Boolean> {
        log.info("reserved push alert at {}", reservedEmailForm.atTime)
        return ResponseEntity.ok(pushRegisterService.reservePushAlert(reservedEmailForm))
    }
}