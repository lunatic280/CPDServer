package com.example.CPD.controller

import com.example.CPD.data.LocationDto
import com.example.CPD.service.LocationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class LocationController(
    private val locationService: LocationService
) {

    @PostMapping("/dog/location")
    fun saveLocation(@RequestBody locationDto: LocationDto): ResponseEntity<Any> {
        val savedLocation = locationService.saveLocation(locationDto)
        return ResponseEntity.ok(savedLocation)
    }
}