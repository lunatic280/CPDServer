package com.example.CPD.data

import com.example.CPD.entity.Dog
import com.example.CPD.entity.Users

data class DogDto(
    val id: Long?,
    val dogName: String,
    val breed: String,
    val age: Int,
    val user: Users
) {

    fun toDog(): Dog {
        return Dog(dogName = dogName, breed = breed, age = age, user = user)
    }
}
