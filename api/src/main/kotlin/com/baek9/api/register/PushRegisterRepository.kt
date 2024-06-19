package com.baek9.api.register

import com.baek9.domain.register.ReservedPushRegister
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface PushRegisterRepository : JpaRepository<ReservedPushRegister, Long> {

    @Query("select r from ReservedPushRegister r where r.atTime >= :current and r.atTime < :end and r.commited = false")
    fun getNotCommitedPeriodReservation(current: LocalDateTime, end: LocalDateTime): List<ReservedPushRegister>
}