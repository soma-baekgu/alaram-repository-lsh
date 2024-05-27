package com.baek9.batch.batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class JobListener : JobExecutionListener {
    val log = LoggerFactory.getLogger(JobListener::class.java)
    override fun beforeJob(jobExecution: JobExecution) {
        log.info("time {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        jobExecution.executionContext.putString("time", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }
}