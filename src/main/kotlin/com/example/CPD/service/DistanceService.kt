package com.example.CPD.service

import com.example.CPD.data.LocationDto
import com.example.CPD.repository.DistanceRepository
import com.example.CPD.utils.Location
import org.springframework.stereotype.Service
import kotlin.math.*


@Service
class DistanceService(
    private val distanceRepository: DistanceRepository,
    private val location: Location
) {
    var flag = false
    var sumDistance = 0.0
    var beforeLat: Double? = null
    var beforeLon: Double? = null

    private fun reset() {
        sumDistance = 0.0
        beforeLat = null
        beforeLon = null
    }

    fun sumDistance(locationDto: LocationDto): Double {
        val lat = locationDto.latitude
        val lon = locationDto.longitude

        if (beforeLat == null || beforeLon == null) {
            beforeLat = lat
            beforeLon = lon
            return sumDistance
        }

        val distance = haversine(beforeLat!!, beforeLon!!, lat, lon)
        sumDistance += distance

        beforeLat = lat
        beforeLon = lon

        return sumDistance
    }

    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371e3 // 지구 반지름(m)
        val phi1 = Math.toRadians(lat1)
        val phi2 = Math.toRadians(lat2)
        val deltaPhi = Math.toRadians(lat2 - lat1)
        val deltaLambda = Math.toRadians(lon2 - lon1)

        val a = sin(deltaPhi / 2).pow(2.0) +
                cos(phi1) * cos(phi2) * sin(deltaLambda / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c // 결과(m)
    }
}