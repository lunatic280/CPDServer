package com.example.CPD.data

import com.example.CPD.entity.Distance
import com.example.CPD.entity.Users

data class DistanceDto(
    val id: Long? = null,
    val distance: Double,
    val startTime: String,
    val endTime: String,
    val user: UserDto
) {
    fun toEntity(user: Users): Distance {
        return Distance(id = null, distance = distance, startTime = startTime, endTime = endTime, user = user)
    }
}
