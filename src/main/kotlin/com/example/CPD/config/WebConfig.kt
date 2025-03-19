//package com.example.CPD.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.servlet.config.annotation.CorsRegistry
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
//
//@Configuration
//class WebConfig {
//
//    @Bean
//    fun corsConfigurer(): WebMvcConfigurer {
//        return object : WebMvcConfigurer {
//            override fun addCorsMappings(registry: CorsRegistry) {
//                registry.addMapping("/**") // 모든 API 경로 허용
//                    .allowedOrigins("http://10.0.2.2:39222") // 에뮬레이터 허용
//                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                    .allowCredentials(true)
//            }
//        }
//
//    }
//}