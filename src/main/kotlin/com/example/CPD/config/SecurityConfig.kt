package com.example.CPD.config

import com.example.CPD.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    lateinit var userService: UserService
    lateinit var objectMapper : ObjectMapper

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/signup", "/", "/api/auth/login").permitAll()
                    .anyRequest().authenticated()
            }
            .logout {
                it.logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }
}