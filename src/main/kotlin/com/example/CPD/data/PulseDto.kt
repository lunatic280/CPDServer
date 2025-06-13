package com.example.CPD.data

import com.example.CPD.entity.Pulse
import java.time.LocalDateTime

data class PulseDto(
    val id: Long? = null,
    val signal: Int,
    val timeStamp: LocalDateTime
) {
    companion object {
        fun fromEntity(pulse: Pulse): PulseDto {
            return PulseDto(
                id = pulse.id,
                signal = pulse.signal,
                timeStamp = pulse.timeStamp
            )
        }
    }
}
