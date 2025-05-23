package com.example.CPD.service

import com.example.CPD.data.LocationDto
import com.example.CPD.entity.Location
import com.example.CPD.repository.LocationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class LocationService(
    private val locationRepository: LocationRepository
) {

    @Transactional
    fun saveLocation(locationDto: LocationDto): LocationDto {
        val savedLocation = Location(null, latitude = locationDto.latitude, longitude = locationDto.longitude)
        locationRepository.save(savedLocation)
        return locationDto
    }
}