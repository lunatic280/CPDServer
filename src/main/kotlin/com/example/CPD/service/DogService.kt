package com.example.CPD.service

import com.example.CPD.data.DogDto
import com.example.CPD.entity.Dog
import com.example.CPD.repository.DogRepository
import com.example.CPD.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class DogService(
    private val dogRepository: DogRepository,
    private val userRepository: UserRepository
) {

    fun save(dogDto: DogDto): Dog {
        val dog = Dog(
            dogName = dogDto.dogName,
            breed = dogDto.breed,
            age = dogDto.age,
            user = dogDto.user
        )
        return dogRepository.save(dog)
    }

    fun delete(id: Long) {
        return dogRepository.deleteById(id)
    }

    fun getDog(id: Long): DogDto {
        val findDog = dogRepository.findById(id).orElseThrow { EntityNotFoundException("아이디를 갖는 개가 없습니다") }
        return findDog.toDto()
    }

    //유저의 이름으로 강아지를 조회한다
    fun getUserDog(email: String): List<DogDto> {
        val user = userRepository.findByEmail(email)
            ?: throw EntityNotFoundException("해당 이메일의 유저가 없습니다.")
        return dogRepository.findByUser(user).map { it.toDto() }
    }
}