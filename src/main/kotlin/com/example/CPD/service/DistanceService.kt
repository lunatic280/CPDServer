package com.example.CPD.service

import com.example.CPD.data.DistanceDto
import com.example.CPD.data.LocationDto
import com.example.CPD.entity.Distance
import com.example.CPD.repository.DistanceRepository
import com.example.CPD.repository.UserRepository
import com.example.CPD.utils.Location
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.*


@Service
class DistanceService(
    private val distanceRepository: DistanceRepository, private val userRepository: UserRepository,
) {

    @Transactional
    fun savedDistance(distanceDto: DistanceDto): Distance {
        val userId = distanceDto.user.
        val user = userRepository.findByEmail(userEmail)
            ?: throw IllegalArgumentException("맞는 이메일이 없습니다.")
        val savedDistance = distanceDto.toEntity(user)
        return distanceRepository.save(savedDistance)
    }
}