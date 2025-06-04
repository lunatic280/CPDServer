package com.example.CPD.entity

import jakarta.persistence.*

@Entity
class Distance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val distance: Double
) {

}