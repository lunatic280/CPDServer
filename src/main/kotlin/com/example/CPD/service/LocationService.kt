package com.example.CPD.service

import com.example.CPD.data.LocationDto
import com.example.CPD.entity.Location
import com.example.CPD.repository.LocationRepository
import com.example.CPD.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class LocationService(
    private val locationRepository: LocationRepository, private val userRepository: UserRepository
) {

    @Transactional
    fun saveLocation(locationDto: LocationDto): LocationDto {
        val ownerEmail = locationDto.user.email
        val owner = userRepository.findByEmail(ownerEmail)
            ?: throw IllegalArgumentException("해당 이메일을 사용하는 사용자를 찾을 수 없습니다.")
        val savedLocation =locationDto.toEntity(owner)
        locationRepository.save(savedLocation)
        return locationDto
    }
}