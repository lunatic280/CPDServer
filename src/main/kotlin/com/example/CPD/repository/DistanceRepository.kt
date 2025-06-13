package com.example.CPD.repository

import com.example.CPD.entity.Distance
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface DistanceRepository : JpaRepository<Distance, Long> {
}