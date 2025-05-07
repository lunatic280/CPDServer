package com.example.CPD.service

import com.example.CPD.dto.BlogDto
import com.example.CPD.entity.Blog
import com.example.CPD.repository.BlogRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import jakarta.websocket.server.PathParam
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service

@Service
class BlogService(
    private val blogRepository: BlogRepository
) {

    @Transactional
    fun create(blog: Blog) : Blog {
        return blogRepository.save(blog)
    }

    @Transactional
    fun update(id: Long, blogDto: BlogDto, currentAuthor: String) : Blog {
        val blog = blogRepository.findById(id).orElseThrow { EntityNotFoundException("해당 블로그가 없습니다.") }

        if (blog.author.email != currentAuthor) {
            throw AccessDeniedException("작성자만 수정할 수 있습니다.")
        }
        blog.update(blogDto.title, blogDto.content)
        return blogRepository.save(blog)
    }

    @Transactional
    fun remove(id: Long) {
        return blogRepository.deleteById(id)
    }

    fun getById(id: Long) : Blog {
        return blogRepository.findById(id).orElseThrow()
    }

    fun getAll(): List<Blog> {
        return blogRepository.findAll()
    }


}