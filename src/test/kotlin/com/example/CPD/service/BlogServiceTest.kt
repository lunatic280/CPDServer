package com.example.CPD.service

import com.example.CPD.entity.Blog
import com.example.CPD.repository.BlogRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.NoSuchElementException


@ExtendWith(MockitoExtension::class)
class BlogServiceTest {

    private val blogRepository: BlogRepository = mock(BlogRepository::class.java)
    private val blogService = BlogService(blogRepository)
    val testBlog = Blog.create(title = "Test Title", content = "Test Content")
    val testBlog2 = Blog.create(title = "Test Title1", content = "Test Content1")
    @Test
    @DisplayName("블로그 모든 글을 조회한다.")
    fun testGetAllBlog() {
        whenever(blogRepository.findAll()).thenReturn(listOf(testBlog, testBlog2))
        val getAllBlog = blogService.getAll()

        assertNotNull(getAllBlog)
        assertEquals(getAllBlog[0].title, "Test Title")
        assertEquals(getAllBlog[0].content, "Test Content")
        assertEquals(getAllBlog[1].title, "Test Title1")
        assertEquals(getAllBlog[1].content, "Test Content1")
    }

    @Test
    @DisplayName("특정 id로 블로그 글을 조회한다.")
    fun testGetById() {
        whenever(blogRepository.findById(1L)).thenReturn(Optional.of(testBlog))
        val findById = blogService.getById(1L)
        //findById.id = 1L // 테스트할 때는 blog set설정을 지우고 테스트하기
        
        assertNotNull(findById)
        println(findById.id)
        assertEquals(1L, findById.id)
    }

    @Test
    @DisplayName("getById메소드가 블로그 글을 찾지 못할 경우 예외를 발생한다.")
    fun testExceptionGetById() {
        whenever(blogRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<NoSuchElementException> {
            blogService.getById(1L)
        }
    }

    @Test
    @DisplayName("블로그 글을 생성한다.")
    fun testCreateBlog() {
        //save()메서드가 호출되면 testBlog를 반환하도록 설정
        whenever(blogRepository.save(any())).thenReturn(testBlog)

        val savedBlog = blogService.create(testBlog)

        assertNotNull(savedBlog)
        assertEquals("Test Title", savedBlog.title)
        assertEquals("Test Content", savedBlog.content)
    }

    @Test
    @DisplayName("블로그 글을 찾아서 업데이트한다.")
    fun testUpdateBlog() {
        val updateBlog = Blog.create(title = "Update Blog", content = "Update Content")
        whenever(blogRepository.findById(1L)).thenReturn(Optional.of(testBlog))
        whenever(blogRepository.save(any())).thenReturn(updateBlog)
        //updateBlog.id = 1L

        val result = blogService.update(1L, updateBlog)

        assertEquals("Update Blog", updateBlog.title)
        assertEquals("Update Content", updateBlog.content)
    }

    @Test
    @DisplayName("블로그 글을 찾아서 삭제한다.")
    fun testDeleteBlog() {
        // 아무동작도 하지 않도록 설정
        doNothing().whenever(blogRepository).deleteById(1L)
        blogService.remove(1L)
        //정확히 한 번 호출되었는지 확인
        verify(blogRepository, times(1)).deleteById(1L)
    }


}