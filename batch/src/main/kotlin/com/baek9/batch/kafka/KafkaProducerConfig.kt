package com.baek9.batch.kafka

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig {
    val log = LoggerFactory.getLogger(KafkaProducerConfig::class.java)

    @Value("\${spring.kafka.bootstrap-servers[0]}")
    lateinit var bootstrapServer: String

    @Bean
    fun producerConfig(): Map<String, Any> {
        val props: HashMap<String, Any> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServer
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

        return props
    }

    @Bean
    fun producerFactory() : ProducerFactory<String, String> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    @Bean
    fun kafkaTemplate() : KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory())
    }

}