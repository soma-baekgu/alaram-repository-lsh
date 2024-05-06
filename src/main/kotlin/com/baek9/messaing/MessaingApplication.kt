package com.baek9.messaing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class MessaingApplication

fun main(args: Array<String>) {
    runApplication<MessaingApplication>(*args)
}