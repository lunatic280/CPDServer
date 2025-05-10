package com.example.CPD.oauth

import com.example.CPD.entity.Users
import com.example.CPD.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserService(
    private val userRepository: UserRepository
): DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuthUser = super.loadUser(userRequest)

        val kakaoAccount = oAuthUser.attributes["kakao_account"] as Map<*,*>
        val email = kakaoAccount["email"] as? String
            ?: throw OAuth2AuthenticationException("Email not found")

        val properties = oAuthUser.attributes["properties"] as Map<*,*>
        val nickname = properties["nickname"] as? String
            ?: email.substringBefore("@")
        val providerId = oAuthUser.attributes["id"].toString()

        val user = userRepository.findByEmail(email) ?: run {
            val newUser = Users.createOAuth(nickname, email, "kakao", providerId)
            userRepository.save(newUser)
        }

        return DefaultOAuth2User(
            emptyList(),
            mapOf(
                "id" to user.id,
                "email" to user.email
            ),
            "email"
        )
    }
}