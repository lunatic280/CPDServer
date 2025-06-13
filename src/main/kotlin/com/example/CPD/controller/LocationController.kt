package com.example.CPD.controller

import com.example.CPD.data.DistanceDto
import com.example.CPD.data.LocationDto
import com.example.CPD.entity.Distance
import com.example.CPD.service.DistanceService
import com.example.CPD.service.LocationService
import com.example.CPD.service.MergeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class LocationController(
    private val locationService: LocationService,
    private val distanceService: DistanceService,
    private val mergeService: MergeService
) {

    @PostMapping("/dog/location")
    fun saveLocation(@RequestBody locationDto: LocationDto): ResponseEntity<Any> {
        val savedLocation = locationService.saveLocation(locationDto)
        return ResponseEntity.ok(savedLocation)
    }

    @PostMapping("/dog/distance")
    fun savedDistance(@RequestBody distanceDto: DistanceDto): ResponseEntity<Any> {
        val savedDistance = distanceService.savedDistance(distanceDto)
        val mergeData = mergeService.MergeDataSet(distanceDto)
        return ResponseEntity.ok(savedDistance to mergeData)
    }
}