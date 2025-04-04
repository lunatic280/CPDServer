package com.example.CPD.controller

import com.example.CPD.entity.Blog
import com.example.CPD.entity.Users
import com.example.CPD.service.BlogService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.mockito.Mockito.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.kotlin.any
import org.springframework.http.MediaType

@WebMvcTest(TestController::class)
class TestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var blogService: BlogService

    private val objectMapper = ObjectMapper()

    val testUser = Users.create(name = "test Name", password = "password", email = "testEmail")
    val testBlog = Blog.create(title = "Test Title", content = "Test Content", author = testUser)

    @Test
    @DisplayName("특정 ID로 블로그 글을 조회한다")
    fun testGetBlog() {
        `when`(blogService.getById(1L)).thenReturn(testBlog)

        mockMvc.perform(get("/api/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Test Title"))
            .andExpect(jsonPath("$.content").value("Test Content"))
    }

    @Test
    @DisplayName("모든 블로그 글을 조회한다.")
    fun testGetById() {
        `when`(blogService.getAll()).thenReturn(listOf(testBlog, Blog.create("Test", "content", testUser)))

        mockMvc.perform(get("/api/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("Test Title"))
            .andExpect(jsonPath("$[1].title").value("Test"))
            .andExpect(jsonPath("$[0].content").value("Test Content"))
            .andExpect(jsonPath("$[1].content").value("content"))

    }

    @Test
    @DisplayName("블로그 글을 생성한다.")
    fun testCreateBlog() {
        `when`(blogService.create(any())).thenReturn(testBlog)

        mockMvc.perform(post("/api/create-blog")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testBlog)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Test Title"))
            .andExpect(jsonPath("$.content").value("Test Content"))
    }

    @Test
    @DisplayName("블로그 글을 업데이트한다.")
    fun testUpdateBlog() {
        val updateBlog = Blog.create("Update Title", "Update Content", testUser)
        `when`(blogService.update(eq(1L), any())).thenReturn(updateBlog)

        mockMvc.perform(put("/api/update/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateBlog)))
            .andExpect(jsonPath("$.title").value("Update Title"))
            .andExpect(jsonPath("$.content").value("Update Content"))

    }

    @Test
    @DisplayName("블로그 글을 삭제한다.")
    fun testDeleteBlog() {
        doNothing().`when`(blogService).remove(1L)

        mockMvc.perform(delete("/api/1"))
            .andExpect(status().isOk)

        verify(blogService, times(1)).remove(1L)
    }
}