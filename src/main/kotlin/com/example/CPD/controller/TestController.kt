package com.example.CPD.controller

import com.example.CPD.data.MessageResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TestController {

    @GetMapping("/hello")
    fun hello(): ResponseEntity<MessageResponse> {
        val response = MessageResponse("Hello, Kotlin Server!")
        return ResponseEntity.ok(response)
    }
}