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

    @Column(nullable = true)
    var password: String = ""
        protected set

    @Column(nullable = true)
    var provider: String? = null
        protected set

    @Column(nullable = true)
    var providerId: String? = null
        protected set

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var blogs: MutableList<Blog?> = mutableListOf()

    @OneToMany(mappedBy = "name", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var dogs: MutableList<Dog?> = mutableListOf()


    protected constructor() : this(null)

    companion object {

        fun createOAuth(name: String, email: String, provider: String, providerId: String): Users {
            return Users().apply {
                this.name = name
                this.email = email
                this.provider = provider
                this.providerId  = providerId
                this.blogs = mutableListOf()
                this.dogs = mutableListOf()
                this.password = ""
            }
        }

        fun create(name: String, password: String, email: String): Users {
            return Users().apply {
                this.name = name
                this.password = password
                this.email = email
                this.blogs = mutableListOf()
                this.dogs = mutableListOf()
            }
        }
    }


}