package com.example.CPD.controller

import com.example.CPD.data.LoginDto
import com.example.CPD.data.UserDto
import com.example.CPD.entity.Users
import com.example.CPD.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.math.log

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/signup")
    fun signUser(@RequestBody userDto: UserDto): ResponseEntity<Map<String, String>> {

        //TODO 중복체크 필요

        userService.create(userDto)
        return ResponseEntity.ok(mapOf("message" to "회원가입 성공!"))
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody loginDto: LoginDto, request: HttpServletRequest): ResponseEntity<Map<String, String>> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        val session: HttpSession = request.getSession(true)
        session.setAttribute("USER", authentication.principal)

        return ResponseEntity.ok(mapOf("message" to "로그인 성공"))
    }


}