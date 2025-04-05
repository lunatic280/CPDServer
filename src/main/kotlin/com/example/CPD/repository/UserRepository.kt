package com.example.CPD.repository

import com.example.CPD.entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<Users, Long> {
    fun findByEmail(email: String): Users?
}