package com.example.CPD.dto

import com.example.CPD.entity.Blog
import com.example.CPD.entity.Users

data class BlogDto(
    val id: Long? = null,
    val title: String,
    val content: String,
    val author: Users
) {

    companion object {
        fun fromEntity(blog: Blog): BlogDto {
            return BlogDto(
                id = blog.id,
                title = blog.title,
                content = blog.content,
                author = blog.author
            )
        }
    }

    fun toEntity(): Blog {
        return Blog.create(title, content, author)
    }

    fun updateEntity(blog: Blog) {
        blog.update(title, content)
    }
}
