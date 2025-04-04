package com.example.CPD.entity

import jakarta.persistence.*

@Entity
class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {

    @Column(nullable = false, unique = true)
    var name: String = ""
        protected set

    @Column(nullable = false, unique = true, length = 30)
    var email: String = ""
        protected set

    @Column(nullable = false)
    var password: String = ""
        protected set

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var blogs: MutableList<Blog?> = mutableListOf()


    protected constructor() : this(null)

    companion object {
        fun create(name: String, password: String, email: String): Users {
            return Users().apply {
                this.name = name
                this.password = password
                this.email = email
                this.blogs = mutableListOf()
            }
        }
    }


}