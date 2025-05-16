package com.example.CPD.data

import com.example.CPD.entity.Dog
import com.example.CPD.entity.Users

data class DogDto(
    val id: Long? = null,
    val dogName: String,
    val age: Int,
    val weight: Int,
    val breed: String,
    val owner: UserDto
) {
    companion object {
        fun fromEntity(dog: Dog): DogDto {
            return DogDto(
                id = dog.id,
                dogName = dog.dogName,
                age = dog.age,
                weight = dog.weight,
                breed = dog.breed,
                owner = UserDto.fromEntity(dog.owner)
            )
        }
    }

    fun toEntity(owner: Users): Dog {
        return Dog(id, dogName, age, weight, breed, owner)
    }


}
