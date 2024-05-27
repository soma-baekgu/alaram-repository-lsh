package com.baek9.batch.datasource

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import javax.sql.DataSource


@Configuration
class DataSourceConfiguration {
    @Value("\${spring.datasource.url}")
    lateinit var url: String

    @Value("\${spring.datasource.username}")
    lateinit var username: String

    @Value("\${spring.datasource.password}")
    lateinit var password: String

    @Value("\${spring.datasource.driver-class-name}")
    lateinit var driverClassName: String

    @Bean
    fun dataSource() : DataSource {
        val driverManagerDataSource = DriverManagerDataSource()
        driverManagerDataSource.setDriverClassName(driverClassName)
        driverManagerDataSource.url = url
        driverManagerDataSource.username = username
        driverManagerDataSource.password = password
        return driverManagerDataSource
    }

    @Bean
    fun jpaTransactionManager(entityManagerFactory: EntityManagerFactory): JpaTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

}