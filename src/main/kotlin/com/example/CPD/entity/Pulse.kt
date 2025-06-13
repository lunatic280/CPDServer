package com.example.CPD.entity

import com.example.CPD.data.PulseDto
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Pulse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "signal_value", nullable = false)
    val signal: Int,

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val timeStamp: LocalDateTime
) {
    fun toDto(): PulseDto {
        return PulseDto(id, signal, timeStamp)
    }
}