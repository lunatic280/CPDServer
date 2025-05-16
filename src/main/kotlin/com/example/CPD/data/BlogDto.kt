package com.example.CPD.dto

import com.example.CPD.data.UserDto
import com.example.CPD.entity.Blog
import com.example.CPD.entity.Users

data class BlogDto(
    val id: Long? = null,
    val title: String,
    val content: String,
    val author: UserDto
) {

    companion object {
        fun fromEntity(blog: Blog): BlogDto {
            return BlogDto(
                id = blog.id,
                title = blog.title,
                content = blog.content,
                author = UserDto.fromEntity(blog.author)
            )
        }
    }

    fun toEntity(authorEntity: Users): Blog {
        return Blog.create(title, content, authorEntity)
    }

}
