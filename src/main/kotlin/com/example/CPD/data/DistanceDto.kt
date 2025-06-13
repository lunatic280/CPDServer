package com.example.CPD.data

import com.example.CPD.entity.Distance
import com.example.CPD.entity.Users
import java.time.LocalDateTime

data class DistanceDto(
    val id: Long? = null,
    val distance: Double,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val user: UserDto
) {
    fun toEntity(user: Users): Distance {
        return Distance(id = null, distance = distance, startTime = startTime, endTime = endTime, user = user)
    }
}
