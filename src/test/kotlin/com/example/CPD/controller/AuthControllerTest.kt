package com.example.CPD.controller

import com.example.CPD.config.SecurityConfig
import com.example.CPD.data.LoginDto
import com.example.CPD.data.UserDto
import com.example.CPD.entity.Users
import com.example.CPD.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.assertNotNull

@WebMvcTest(AuthController::class)
@Import(SecurityConfig::class)
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var userService: UserService

    @MockitoBean
    private lateinit var authenticationManager: AuthenticationManager

    @Test
    @DisplayName("회원가입 성공시  200 ok와 성공 메시지를 반환한다")
    fun signupSuccess() {
        val userDto = UserDto(name="홍길동", email = "test@com", password = "password")
        val createdUser = Users.create("홍길동", "password", "test@com")
        whenever(userService.create(any())).thenReturn(createdUser)

        mockMvc.perform(
            post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message")
                .value("회원가입 성공: ${createdUser.email}"))

    }

    @Test
    @DisplayName("로그인 성공시 200 ok와 성공메시지를 반환한다")
    fun loginSuccess() {
        val loginDto = LoginDto("testEmail", "password")
        val dummyAuth = UsernamePasswordAuthenticationToken(
            loginDto.email,
            loginDto.password,
            emptyList()
        )
        whenever(authenticationManager.authenticate(any<UsernamePasswordAuthenticationToken>()))
            .thenReturn(dummyAuth)

        val result = mockMvc.perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value("로그인 성공"))
            .andReturn()

        val session = result.request.session
        assertNotNull(session, "세션이 생성되어야 합니다")
        assertNotNull(
            session!!.getAttribute("SPRING_SECURITY_CONTEXT"),
            "SPRING_SECURITY_CONTEXT 속성이 세션에 저장되어야 합니다"
        )
    }

//    @Test
//    @DisplayName("로그인 실패시 401 Unauthorized와 오류 메시지를 반환한다")
//    fun loginFailed() {
//        val loginDto = LoginDto(email = "testEmail", password = "password")
//        whenever(authenticationManager.authenticate(any()))
//            .thenThrow(BadCredentialsException("Bad credentials"))
//
//        mockMvc.perform(
//            post("/api/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginDto))
//        )
//            .andExpect(status().isUnauthorized)
//            .andExpect(jsonPath("$.message")
//                .value(org.hamcrest.Matchers.containsString("로그인 실패")))
//    }
}