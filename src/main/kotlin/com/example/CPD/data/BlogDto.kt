package com.example.CPD.dto

import com.example.CPD.entity.Blog

data class BlogDto(
    val id: Long? = null,
    val title: String,
    val content: String
) {

    companion object {
        fun fromEntity(blog: Blog): BlogDto {
            return BlogDto(
                id = blog.id,
                title = blog.title,
                content = blog.content
            )
        }
    }

    fun toEntity(): Blog {
        return Blog.create(title, content)
    }

    fun updateEntity(blog: Blog) {
        blog.update(title, content)
    }
}
