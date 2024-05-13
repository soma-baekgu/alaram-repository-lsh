package com.baek9.messaing.register.entity

import jakarta.persistence.*
import jakarta.persistence.GenerationType.*
import lombok.AccessLevel.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class ReservedPushRegister(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column
    val email: String,

    @Column
    val title: String,

    @Column
    val message: String,

    @Column
    val atTime: LocalDateTime
)