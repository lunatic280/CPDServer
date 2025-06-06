package com.example.CPD.data

import com.example.CPD.entity.Users
import org.apache.catalina.User

data class UserDto(
    val id: Long? = null,
    val name: String,
    val email: String,
    val password: String? = null
) {
    companion object {
        fun fromEntity(users: Users): UserDto {
            return UserDto(
                id = users.id,
                name = users.name,
                email = users.email,
                password = users.password
            )
        }
    }

    fun toEntity(): Users {
        return Users.create(name, email, password ?: "")
    }


}
