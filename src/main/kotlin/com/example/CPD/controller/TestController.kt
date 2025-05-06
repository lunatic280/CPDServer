package com.example.CPD.controller

import com.example.CPD.dto.BlogDto
import com.example.CPD.data.MessageResponse
import com.example.CPD.entity.Blog
import com.example.CPD.service.BlogService
import com.example.CPD.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class TestController(
    private val blogService: BlogService,
    private val userService: UserService
) {

    @GetMapping("/hello")
    fun hello(): ResponseEntity<MessageResponse> {
        val response = MessageResponse("Hello, Kotlin Server!")
        return ResponseEntity.ok(response)
    }

    @GetMapping("/")
    fun getAllBlog(): List<BlogDto> {
        return blogService.getAll()
            .map { BlogDto.fromEntity(it) }
    }


    @GetMapping("/{id}")
    fun getBlog(@PathVariable id: Long) : BlogDto {
        return blogService.getById(id)
            .let { BlogDto.fromEntity(it) }
    }

    @PostMapping("/create-blog")
    fun createBlog(@RequestBody blogDto: BlogDto): ResponseEntity<Any> {
        val authorEmail = blogDto.author.email

        // 👇 인증된 사용자의 이메일 가져오기
        val authenticatedUserEmail = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username

        // 👇 보안 체크: 인증된 사용자 이메일과 요청된 이메일 일치 여부 확인
        if (authenticatedUserEmail != authorEmail) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("인증된 사용자와 요청자가 일치하지 않습니다.")
        }

        val authorEntity = userService.findByEmail(authorEmail)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 없음")

        val blogEntity = blogDto.toEntity(authorEntity)
        val savedBlog = blogService.create(blogEntity)

        return ResponseEntity.ok(BlogDto.fromEntity(savedBlog))
    }


    @PutMapping("/update/{id}")
    fun updatedBlog(@PathVariable id: Long, @RequestBody blogDto: BlogDto) : ResponseEntity<Any> {
        val authorEmail = blogDto.author.email

        val authenticatedUserEmail = (SecurityContextHolder.getContext().authentication.principal as UserDetails).username

        if (authenticatedUserEmail != authorEmail) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("인증된 사용자와 요청자가 일치하지 않습니다")
        }

        val authorEntity = userService.findByEmail(authorEmail)
        val targetBlog = blogDto.toEntity(authorEntity)
        return ResponseEntity.ok(blogService.update(id,targetBlog))
    }

    @DeleteMapping("/{id}")
    fun deletedBlog(@PathVariable id: Long) {
        return blogService.remove(id)
    }
}