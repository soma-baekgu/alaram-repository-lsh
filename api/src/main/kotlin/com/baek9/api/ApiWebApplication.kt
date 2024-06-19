package com.baek9.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
@EntityScan(basePackages = ["com.baek9.domain"])
class ApiWebApplication

fun main(args: Array<String>) {
    runApplication<ApiWebApplication>(*args)
}
