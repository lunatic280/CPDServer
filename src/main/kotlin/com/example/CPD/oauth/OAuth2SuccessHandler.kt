package com.example.CPD.oauth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class OAuth2SuccessHandler(
    private val tokenProvider: TokenProvider
): SimpleUrlLogoutSuccessHandler(), AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val principal = authentication.principal as OAuth2User
        val email = principal.getAttribute<String>("email")
        val tokensNullable: TokenDto? = tokenProvider.createToken(email.toString())
        val tokens = tokensNullable
            ?: throw IllegalStateException("토큰 생성 실패")

        val uri = UriComponentsBuilder
            .fromUriString("http://localhost:8080/api/sign/login/kakao")
            .queryParam("accessToken", tokens.accessToken)
            .queryParam("refreshToken", tokens.refreshToken)
            .build().toUriString()
        redirectStrategy.sendRedirect(request, response, uri)
    }
}