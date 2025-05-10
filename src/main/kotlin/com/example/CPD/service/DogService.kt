package com.example.CPD.service

import com.example.CPD.data.DogDto
import com.example.CPD.entity.Dog
import com.example.CPD.repository.DogRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class DogService(
    private val dogRepository: DogRepository
) {

    fun save(dogDto: DogDto): Dog {
        return dogRepository.save(dogDto.toDog())
    }

    fun delete(id: Long) {
        return dogRepository.deleteById(id)
    }

    fun getDog(id: Long): DogDto {
        val findDog = dogRepository.findById(id).orElseThrow { EntityNotFoundException("아이디를 갖는 개가 없습니다") }
        return findDog.toDto()
    }

    fun getAllDog(): List<DogDto?> {
        return dogRepository.findAll().map { it.toDto() }
    }
}