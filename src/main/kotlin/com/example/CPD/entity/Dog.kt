package com.example.CPD.entity

import com.example.CPD.data.DogDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Dog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val dogName: String = "이름을 지어주세여",

    @Column(nullable = false)
    val age: Int = 0,

    @Column(nullable = false)
    val weight: Int = 0,

    @Column(nullable = false)
    val breed: String = "똥개",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var owner: Users

) {
    protected constructor() : this(null, "", 0, 0, "", Users())


}