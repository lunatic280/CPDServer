package com.example.CPD.data

import com.example.CPD.entity.PythonData
import java.time.LocalDateTime

data class PythonDataDto(
    val id: Long? = null,
    val stringTest: String,
    val intTest: Int,
    val booleanTest: Boolean,
    val timestamp: LocalDateTime
) {
    fun toEntity(): PythonData {
        return PythonData(null, stringTest, intTest, booleanTest, timestamp)
    }
}
