package com.example.CPD.service

import com.example.CPD.data.UserDto
import com.example.CPD.entity.Users
import com.example.CPD.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    @Transactional
    fun create(userDto: UserDto): Users {
        val createUser = Users.create(
            userDto.name,
            bCryptPasswordEncoder.encode(userDto.password),
            userDto.email
        )
        return userRepository.save(createUser)
    }
    @Transactional(readOnly = true)
    fun findByEmail(email: String): Users {
        return userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("해당 이메일의 사용자가 없습니다.")
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found with email")

        return User(user.email, user.password, emptyList())
    }

}