package com.example.CPD.entity

import com.example.CPD.data.PythonDataDto
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class PythonData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val intTest: Int = 0,

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")  // Python에서 ISO 포맷으로 보낼 때
    val timestamp: LocalDateTime
) {
    fun toDto(): PythonDataDto {
        return PythonDataDto(intTest = intTest, timestamp = timestamp)
    }
}