package com.example.CPD.repository

import com.example.CPD.entity.Location
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface LocationRepository: JpaRepository<Location, Long> {
    fun findAllByTimestampBetween(start:LocalDateTime, end: LocalDateTime): List<Location>
}