package com.example.CPD.service

import com.example.CPD.data.PythonDataDto
import com.example.CPD.entity.PythonData
import com.example.CPD.repository.PythonDataRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PythonDataService(
    private val pythonDataRepository: PythonDataRepository
) {

    @Transactional
    fun save(dataDto: PythonDataDto): PythonDataDto {
        val data = dataDto.toEntity()
        return pythonDataRepository.save(data).toDto()
    }
}