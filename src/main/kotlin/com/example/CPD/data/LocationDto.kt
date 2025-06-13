package com.example.CPD.data

import com.example.CPD.entity.Location
import com.example.CPD.entity.Users
import java.time.LocalDateTime

data class LocationDto(
    val id: Long? = null,
    val timestamp: LocalDateTime,
    val latitude: Double,
    val longitude: Double,
    val user: UserDto
) {
    companion object {
        fun fromEntity(location: Location): LocationDto {
            return LocationDto(
                id = null,
                timestamp = location.timestamp,
                latitude = location.latitude,
                longitude = location.longitude,
                user = UserDto.fromEntity(location.user)
            )
        }

    }
    fun toEntity(user: Users): Location {
        return Location(id = null, timestamp = timestamp, latitude = latitude, longitude = longitude, user = user)
    }
}
