package com.example.CPD.controller

import com.example.CPD.data.MessageResponse
import com.example.CPD.entity.Blog
import com.example.CPD.service.BlogService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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


    @GetMapping("/{id}")
    fun getBlog(@PathVariable id: Long) : Blog {
        return blogService.getById(id)
    }

    @PostMapping("/create-blog")
    fun createBlog(blog: Blog) : Blog {
        val savedBlog = Blog(
            title = blog.title,
            content = blog.content
        )
        return blogService.create(savedBlog)
    }

    @PutMapping("/update/{id}")
    fun updatedBlog(@PathVariable id: Long) : Blog {
        val targetBlog = blogService.getById(id)
        return blogService.update(id,targetBlog)
    }

    @DeleteMapping("/{id}")
    fun deletedBlog(@PathVariable id: Long) {
        return blogService.remove(id)
    }
}