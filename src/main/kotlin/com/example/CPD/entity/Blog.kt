package com.example.CPD.entity

import jakarta.persistence.*

@Entity
class Blog(
    title: String,
    content: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false)
    var content: String = content
        protected set

    constructor() : this("", "")

    fun update(blog: Blog) {
        this.title = blog.title
        this.content = blog.content
    }

}
