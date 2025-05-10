package com.example.CPD.oauth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenProvider(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,

    // application.yml: access-token-expire-time
    @Value("\${jwt.access-token-expire-time}")
    private val accessTokenValidity: Long,

    // application.yml: refresh-token-expire-time
    @Value("\${jwt.refresh-token-expire-time}")
    private val refreshTokenValidity: Long
) {
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun createToken(email: String): TokenDto {
        val now = Date().time
        val accessToken = Jwts.builder()
            .setSubject(email)
            .setIssuedAt(Date(now))
            .setExpiration(Date(now + accessTokenValidity * 1000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        val refreshToken = Jwts.builder()
            .setSubject(email)
            .setIssuedAt(Date(now))
            .setExpiration(Date(now + refreshTokenValidity * 1000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        return TokenDto(accessToken, refreshToken)
    }

    fun validateToken(token: String): Boolean =
        runCatching { Jwts.parser().setSigningKey(key).build().parseClaimsJwt(token) }.isSuccess

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parser().setSigningKey(key).build().parseSignedClaims(token).body
        val principal = User(claims.subject, "", emptyList())
        return UsernamePasswordAuthenticationToken(principal, token, emptyList())
    }

}