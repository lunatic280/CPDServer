package com.example.CPD.repository

import com.example.CPD.entity.Location
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository: JpaRepository<Location, Long> {
}