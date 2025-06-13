package com.example.CPD.repository

import com.example.CPD.entity.Pulse
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface PulseRepository: JpaRepository<Pulse, Long> {
    fun findAllByTimeStampBetween(start: LocalDateTime, end: LocalDateTime): List<Pulse>
}