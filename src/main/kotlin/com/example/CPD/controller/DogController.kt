package com.example.CPD.controller

import com.example.CPD.data.DogDto
import com.example.CPD.entity.Dog
import com.example.CPD.service.DogService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/dog")
class DogController(
    private val dogService: DogService
) {

    @GetMapping("/")
    fun getUserDog(): ResponseEntity<List<DogDto?>> {
        val authenticatedUserEmail = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username

        return ResponseEntity.ok(dogService.getUserDog(authenticatedUserEmail))
    }

    @GetMapping("/{id}")
    fun getDog(@PathVariable id: Long): ResponseEntity<DogDto> {
        val findDog = dogService.getDog(id)
        return ResponseEntity.ok(findDog)
    }

    @PostMapping("/registration-dog")
    fun registration(@RequestBody dogDto: DogDto): ResponseEntity<Any> {
        val userEmail = dogDto.user.email
        val authenticatedUserEmail = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username

        if (authenticatedUserEmail != userEmail) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("인증된 사용자와 요청자가 일치하지 않습니다.")
        }
        val savedDog = dogService.save(dogDto)
        val location = URI.create("/dog/registration-dog/${dogDto.id}")
        return ResponseEntity.created(location).body(savedDog.toDto())
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        dogService.delete(id)
        return ResponseEntity.noContent().build()
    }
}