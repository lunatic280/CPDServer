package com.example.CPD.repository

import com.example.CPD.entity.PythonData
import org.springframework.data.jpa.repository.JpaRepository

interface PythonDataRepository: JpaRepository<PythonData, Long> {
}