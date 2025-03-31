package com.example.CPD.data

import com.example.CPD.entity.Blog

data class BlogDto(
    val title: String,
    val content: String
) {
    fun toEntity() : Blog {
        return Blog(title, content)
    }
}
