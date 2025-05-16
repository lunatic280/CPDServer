package com.example.CPD.repository

import com.example.CPD.entity.Dog
import com.example.CPD.entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface DogRepository: JpaRepository<Dog, Long> {
    fun findByOwner(owner: Users): List<Dog>
}