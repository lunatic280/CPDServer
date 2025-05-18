package com.example.CPD.entity

import com.example.CPD.data.PythonDataDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class PythonData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val stringTest: String = "",

    @Column(nullable = false)
    val intTest: Int = 0,

    @Column(nullable = false)
    val booleanTest: Boolean = false
) {
    fun toDto(): PythonDataDto {
        return PythonDataDto(stringTest = stringTest, intTest = intTest, booleanTest = booleanTest)
    }
}