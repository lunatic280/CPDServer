package com.example.CPD.repository

import com.example.CPD.entity.Blog
import org.springframework.data.jpa.repository.JpaRepository

interface BlogRepository : JpaRepository<Blog, Long> {
}