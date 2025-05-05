package com.example.CPD.controller

import com.example.CPD.data.LoginDto
import com.example.CPD.data.MessageResponse
import com.example.CPD.data.UserDto
import com.example.CPD.entity.Users
import com.example.CPD.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.apache.tomcat.websocket.AuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*
import kotlin.math.log

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/signup")
    fun signUser(@RequestBody userDto: UserDto): ResponseEntity<Any> {
        val createdUser = userService.create(userDto)
       return ResponseEntity.ok(MessageResponse("회원가입 성공: ${createdUser.email}"))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginDto: LoginDto,
        request: HttpServletRequest
    ): ResponseEntity<MessageResponse> {
        return try {
            val authToken = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
            val auth = authenticationManager.authenticate(authToken)

            // 인증 정보 SecurityContext에 저장
            SecurityContextHolder.getContext().authentication = auth

            // 명시적 세션 생성 및 인증 정보 저장
            val session = request.getSession(true)
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext())

            ResponseEntity.ok(MessageResponse("로그인 성공"))
        } catch (e: AuthenticationException) {
            ResponseEntity.status(401).body(MessageResponse("로그인 실패: ${e.message}"))
        }
    }


    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any> {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth != null) {
            SecurityContextLogoutHandler().logout(request, response, auth)
        }
        return ResponseEntity.ok("로그아웃 되었습니다.")
    }

    @GetMapping("/check-session")
    fun checkSession(): ResponseEntity<String> {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication != null && authentication.isAuthenticated) {
            ResponseEntity.ok("인증된 사용자: ${authentication.name}")
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않음")
        }
    }

    @GetMapping("/api/auth/status")
    fun authStatus(request: HttpServletRequest): ResponseEntity<String> {
        val session = request.getSession(false)
        val auth = SecurityContextHolder.getContext().authentication
        return if (session != null && auth != null && auth.isAuthenticated) {
            ResponseEntity.ok("인증 성공: ${auth.name}")
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패")
        }
    }


}