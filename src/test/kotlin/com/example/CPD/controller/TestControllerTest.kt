package com.example.CPD.controller

import com.example.CPD.config.SecurityConfig
import com.example.CPD.data.UserDto
import com.example.CPD.dto.BlogDto
import com.example.CPD.entity.Blog
import com.example.CPD.entity.Users
import com.example.CPD.service.BlogService
import com.example.CPD.service.UserService
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
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.mockito.kotlin.verify
import org.mockito.kotlin.argThat

@WebMvcTest(TestController::class)
@Import(SecurityConfig::class)
class TestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var blogService: BlogService

    @MockitoBean
    private lateinit var userService: UserService

    private val objectMapper = ObjectMapper()

    private val testEmail = "testEmail"
    private val testUser = Users.create(name = "test Name", password = "password", email = testEmail)


    @Test
    @DisplayName("GET /api/ - 블로그 리스트를 반환한다")
    fun getAllBlog() {
        val blog1 = Blog.create("t1", "c1", testUser)
        val blog2 = Blog.create("t2", "c2", testUser)
        whenever (blogService.getAll()).thenReturn(listOf(blog1, blog2))

        mockMvc.perform(
            get("/api/")
                .with(user(testEmail))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("[0].title").value("t1"))
            .andExpect(jsonPath("[0].content").value("c1"))
            .andExpect(jsonPath("[1].title").value("t2"))
            .andExpect(jsonPath("[1].content").value("c2"))
    }
    @Test
    @DisplayName("GET /api/{id} - 존재하는 블로그 ID 요청시 BlogDto를 반환한다")
    fun getBlog() {
        val blog = Blog.create("타이틀", "컨텐츠", testUser)
        whenever (blogService.getById(1L)).thenReturn(blog)

        mockMvc.perform(
            get("/api/1")
                .with(user(testEmail))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("타이틀"))
    }

    @Test
    @DisplayName("POST /api/create-blog - 인증 사용자와 작성장 일치 시 블로그 생성된다")
    fun createBlogSuccess() {
        val newBlogDto = BlogDto(
            title = "새 글",
            content = "내용",
            author = UserDto(name = testUser.name, email = testEmail)
        )

        whenever(userService.findByEmail(testEmail)).thenReturn(testUser)
        val savedBlog = Blog.create("새 글", "내용", testUser)
        whenever(blogService.create(any())).thenReturn(savedBlog)

        mockMvc.perform(
            post("/api/create-blog")
                .with(csrf())
                .with(user(testEmail))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBlogDto))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("새 글"))
            .andExpect(jsonPath("$.author.email").value(testEmail))

        verify(blogService).create(
            org.mockito.kotlin.argThat { blog ->
                blog.title == "새 글" &&
                        blog.author.email == testEmail
            }
        )
    }

    /*@Test
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
    }*/
}