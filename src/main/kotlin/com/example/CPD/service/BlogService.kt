package com.example.CPD.service

import com.example.CPD.entity.Blog
import com.example.CPD.repository.BlogRepository
import jakarta.transaction.Transactional
import jakarta.websocket.server.PathParam
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
    fun update(id: Long, blog: Blog) : Blog {
        return getById(id).apply { update(blog) }
    }

    @Transactional
    fun remove(id: Long) {
        return blogRepository.deleteById(id)
    }

    fun getById(id: Long) : Blog {
        return blogRepository.findById(id).orElseThrow()
    }


}