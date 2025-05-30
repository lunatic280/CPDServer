package com.example.CPD.entity

import com.example.CPD.data.PulseDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Pulse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val beatsPerMinute: Int,

    @Column(nullable = false)
    val interBeatInterval: Int,

    @Column(name= "`signal'", nullable = false)
    val signal: Int,

    @Column(nullable = false)
    val timeStamp: String
) {
    fun toDto(): PulseDto {
        return PulseDto(beatsPerMinute, interBeatInterval, signal, timeStamp)
    }
}