package com.example.CPD.repository

import com.example.CPD.entity.Pulse
import org.springframework.data.jpa.repository.JpaRepository

interface PulseRepository: JpaRepository<Pulse, Long> {
}