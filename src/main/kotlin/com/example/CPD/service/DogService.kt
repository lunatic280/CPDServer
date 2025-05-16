package com.example.CPD.service

import com.example.CPD.data.DogDto
import com.example.CPD.entity.Dog
import com.example.CPD.repository.DogRepository
import com.example.CPD.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.file.AccessDeniedException

@Service
class DogService(
    private val dogRepository: DogRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun create(dogDto: DogDto): DogDto {
        val ownerEmail = dogDto.owner.email
        val owner = userRepository.findByEmail(ownerEmail)
            ?: throw IllegalArgumentException("해당 이메일의 사용자가 존재하지 않습니다: $ownerEmail")
        val dog = dogDto.toEntity(owner)
        val savedDog = dogRepository.save(dog)
        return DogDto.fromEntity(savedDog)
    }

    @Transactional
    fun delete(id: Long, currentUser: String) {
        val dog = dogRepository.findById(id).orElseThrow { EntityNotFoundException("블로그가 없습니다") }
        val dogOwner = dog.owner.email

        if (dogOwner != currentUser) {
            throw AccessDeniedException("작성자만 삭제할 수 있습니다")
        }
        return dogRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun getDogByOwnerEmail(email: String): List<DogDto> {
        val owner = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("해당 이메일을 사용하는 사용자를 찾을 수 없습니다.")

        return dogRepository.findByOwner(owner).map { DogDto.fromEntity(it) }
    }

    @Transactional(readOnly = true)
    fun getDogById(id: Long): DogDto {
        val dogDto = dogRepository.findById(id).orElseThrow { EntityNotFoundException("해당하는 강아지가 없습니다.") }
        return DogDto.fromEntity(dogDto)
    }
}