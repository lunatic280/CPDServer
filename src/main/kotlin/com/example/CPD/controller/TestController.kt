package com.example.CPD.controller

import com.example.CPD.data.BlogDto
import com.example.CPD.data.MessageResponse
import com.example.CPD.entity.Blog
import com.example.CPD.service.BlogService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class TestController(
    private val blogService: BlogService
) {

    @GetMapping("/hello")
    fun hello(): ResponseEntity<MessageResponse> {
        val response = MessageResponse("Hello, Kotlin Server!")
        return ResponseEntity.ok(response)
    }

    @GetMapping("/")
    fun getAllBlog(): List<Blog> {
        return blogService.getAll()
    }


    @GetMapping("/{id}")
    fun getBlog(@PathVariable id: Long) : Blog {
        return blogService.getById(id)
    }

    @PostMapping("/create-blog")
    fun createBlog(@RequestBody blogDto: BlogDto) : Blog {
        val blog = blogDto.toEntity()
        return blogService.create(blog)
    }

    @PutMapping("/update/{id}")
    fun updatedBlog(@PathVariable id: Long, @RequestBody blogDto: BlogDto) : Blog {
        val targetBlog = blogDto.toEntity()
        return blogService.update(id,targetBlog)
    }

    @DeleteMapping("/{id}")
    fun deletedBlog(@PathVariable id: Long) {
        return blogService.remove(id)
    }
}