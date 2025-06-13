package com.example.CPD.data

import com.example.CPD.entity.PythonData
import java.time.LocalDateTime

data class PythonDataDto(
    val id: Long? = null,
    val intTest: Int,
    val timestamp: LocalDateTime
) {
    companion object {
        fun fromEntity(pythonData: PythonData): PythonDataDto {
            return PythonDataDto(
                id = pythonData.id,
                intTest = pythonData.intTest,
                timestamp = pythonData.timestamp
            )
        }
    }

    fun toEntity(): PythonData {
        return PythonData(null, intTest, timestamp)
    }
}
