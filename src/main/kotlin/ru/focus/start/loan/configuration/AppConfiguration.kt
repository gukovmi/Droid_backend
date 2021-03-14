package ru.focus.start.loan.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AppConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder =
            BCryptPasswordEncoder()

    @Bean
    fun restConfiguration(): RepositoryRestConfigurer =
            RestConfiguration()
}