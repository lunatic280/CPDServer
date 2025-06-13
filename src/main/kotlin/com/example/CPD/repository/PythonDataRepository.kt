package com.example.CPD.repository

import com.example.CPD.entity.PythonData
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface PythonDataRepository: JpaRepository<PythonData, Long> {
    fun findAllByTimestampBetween(start: LocalDateTime, end: LocalDateTime): List<PythonData>
}