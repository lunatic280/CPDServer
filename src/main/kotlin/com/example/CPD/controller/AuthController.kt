package com.example.CPD.controller

import com.example.CPD.data.LoginDto
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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
       return ResponseEntity.ok("회원가입 성공: ${createdUser.email}")
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<String> {
        return try {
            val authToken = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
            authenticationManager.authenticate(authToken)
            ResponseEntity.ok("로그인 성공")
        } catch (e: AuthenticationException) {
            ResponseEntity.status(401).body("로그인 실패: ${e.message}")
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


}