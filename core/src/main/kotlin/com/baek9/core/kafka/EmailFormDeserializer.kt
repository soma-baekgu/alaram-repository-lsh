package com.baek9.core.kafka

import com.baek9.domain.email.EmailForm
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory

class EmailFormDeserializer : Deserializer<EmailForm>{
    val log = LoggerFactory.getLogger(EmailFormDeserializer::class.java)

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
        super.configure(configs, isKey)
    }

    override fun deserialize(p0: String?, p1: ByteArray?): EmailForm {
        try {
            val data: String = p1?.toString(Charsets.UTF_8).toString()
            log.info("{}", data)
            val objectMapper: ObjectMapper = jacksonObjectMapper()
            val res: EmailForm = objectMapper.readValue<EmailForm>(data)

            log.info("result {}", res)

            return res
        } catch (e : Exception){
            return EmailForm.ERROR
        }
    }
}