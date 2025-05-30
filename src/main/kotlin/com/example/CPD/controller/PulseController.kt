package com.example.CPD.controller

import com.example.CPD.data.PulseDto
import com.example.CPD.service.PulseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PulseController(
    private val pulseService: PulseService
) {

    @PostMapping("/endpoint")
    fun getPulse(@RequestBody pulseDto: PulseDto): ResponseEntity<Any> {
        val savedPulse = pulseService.savePulse(pulseDto)
        return ResponseEntity.ok(savedPulse)
    }
}