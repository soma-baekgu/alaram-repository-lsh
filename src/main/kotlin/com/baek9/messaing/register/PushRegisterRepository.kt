package com.baek9.messaing.register

import com.baek9.messaing.register.entity.ReservedPushRegister
import org.springframework.data.jpa.repository.JpaRepository

interface PushRegisterRepository : JpaRepository<ReservedPushRegister, Long>