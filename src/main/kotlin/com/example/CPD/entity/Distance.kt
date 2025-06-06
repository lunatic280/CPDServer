package com.example.CPD.entity

import jakarta.persistence.*

@Entity
class Distance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val distance: Double,

    @Column(nullable = false)
    val startTime: String,

    @Column(nullable = false)
    val endTime: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: Users
) {

}