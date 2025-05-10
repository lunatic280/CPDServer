package com.example.CPD.entity

import com.example.CPD.data.DogDto
import jakarta.persistence.*

@Entity
class Dog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = true)
    val name: String = "",

    @Column(nullable = true)
    val breed: String = "",

    @Column(nullable = true)
    val age: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", nullable = false)
    var user: Users

) {

    fun toDto(): DogDto {
        return DogDto(id, name, breed, age, user)
    }
}