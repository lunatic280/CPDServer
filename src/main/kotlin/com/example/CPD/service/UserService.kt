package com.example.CPD.service

import com.example.CPD.data.UserDto
import com.example.CPD.entity.Users
import com.example.CPD.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {


    fun create(userDto: UserDto): Users {
        val createUser = Users.create(userDto.name, bCryptPasswordEncoder.encode(userDto.password), email = userDto.email)

        return userRepository.save(createUser)
    }

}