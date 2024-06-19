package com.baek9.core.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.listener.ContainerProperties
import java.util.HashMap

@Configuration
class KafkaConfig {
    val log = LoggerFactory.getLogger(KafkaConfig::class.java)

    @Value("\${spring.kafka.bootstrap-servers[0]}")
    lateinit var bootstrapServer: String

    @Bean
    fun consumerConfig() : Map<String, Any> {
        val props: HashMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServer
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = EmailFormDeserializer::class.java
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = EmailFormDeserializer::class.java
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false

        return props
    }

    @Bean
    fun consumerFactory() : ConsumerFactory<String, String> {
        return DefaultKafkaConsumerFactory(consumerConfig())
    }


    @Bean
    fun kafkaListenerContainerFactory() :
            KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    {
        val factory: ConcurrentKafkaListenerContainerFactory<String, String> = ConcurrentKafkaListenerContainerFactory()
        factory.consumerFactory = consumerFactory()

        return factory
    }
}