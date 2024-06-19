package com.baek9.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@EntityScan(basePackages = ["com.baek9.domain"])
@SpringBootApplication
class BatchApplication

fun main(args: Array<String>) {
	runApplication<BatchApplication>(*args)
}
