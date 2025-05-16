package com.example.CPD.controller

import com.example.CPD.data.DogDto
import com.example.CPD.service.DogService
import com.example.CPD.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dog")
class DogController(
    private val dogService: DogService,
    private val userService: UserService
) {

    @GetMapping("")
    fun getDogByOwnerEmail(): List<DogDto> {
        val authenticatedUserEmail = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username

        val dogList = dogService.getDogByOwnerEmail(authenticatedUserEmail)
        return dogList
    }

    @GetMapping("/{id}")
    fun getDogById(@PathVariable id: Long): DogDto {
        val dogById = dogService.getDogById(id)
        return dogById
    }

    @PostMapping("/create-dog")
    fun createDog(@RequestBody dogDto: DogDto): ResponseEntity<Any> {
        val ownerEmail = dogDto.owner.email
        val authenticatedUserEmail = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username

        if (ownerEmail != authenticatedUserEmail) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("인증된 사용자가 요청자가 일치하지 않습니다")
        }
        val savedDog = dogService.create(dogDto)
        return ResponseEntity.ok(savedDog)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long) {
        val currentDogOwnerEmail = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username
        return dogService.delete(id, currentDogOwnerEmail)
    }
}