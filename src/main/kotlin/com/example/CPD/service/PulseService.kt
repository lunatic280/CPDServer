package com.example.CPD.service

import com.example.CPD.data.PulseDto
import com.example.CPD.entity.Pulse
import com.example.CPD.repository.PulseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PulseService(
    private val pulseRepository: PulseRepository
) {

    @Transactional
    fun savePulse(pulseDto: PulseDto): PulseDto {
        val savedPulse = Pulse(
            null,
            signal = pulseDto.signal,
            timeStamp = pulseDto.timeStamp
        )
        return pulseRepository.save(savedPulse).toDto()
    }

    fun findLatestPulse(): PulseDto? {
        val latestPulse = pulseRepository.findFirstByOrderByIdDesc()
        return latestPulse?.let { PulseDto.fromEntity(it) }
    }
}