package com.example.CPD.config

import com.example.CPD.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.context.HttpSessionSecurityContextRepository

@Configuration
@EnableWebSecurity
class SecurityConfig {

    lateinit var userService: UserService
    lateinit var objectMapper : ObjectMapper

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { } // ✅ CORS 허용
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/endpoint").permitAll()
                    .requestMatchers("/api/create-blog").authenticated()
                    .anyRequest().authenticated()
            }
            .logout {
                it.logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
            }
            .sessionManagement {
                it
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .maximumSessions(1).maxSessionsPreventsLogin(false)
            }
            .securityContext {
                it.securityContextRepository(HttpSessionSecurityContextRepository())
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