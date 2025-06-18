package com.example.CPD.controller

import com.example.CPD.data.PulseDto
import com.example.CPD.service.PulseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
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

    @GetMapping("/pulse")
    fun getLatestPulse(): ResponseEntity<PulseDto> {
        val latestPulse = pulseService.findLatestPulse()
        return if (latestPulse != null) {
            ResponseEntity.ok(latestPulse)
        } else {
            ResponseEntity.noContent().build()
        }
    }
}