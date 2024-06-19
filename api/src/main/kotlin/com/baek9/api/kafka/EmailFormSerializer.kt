package com.baek9.api.kafka

import com.baek9.domain.email.EmailForm
import org.apache.kafka.common.serialization.Serializer

class EmailFormSerializer : Serializer<EmailForm>{

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
        super.configure(configs, isKey)
    }

    override fun serialize(topic: String?, data: EmailForm?): ByteArray {
        return try {
            data.toString().toByteArray()
        } catch (e : Exception){
            ByteArray(0)
        }
    }
}