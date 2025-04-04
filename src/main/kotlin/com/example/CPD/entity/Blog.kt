package com.example.CPD.entity

import jakarta.persistence.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Protected

@Entity
class Blog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

) {

    @Column(nullable = false)
    var title: String = "제목 없음"
        protected set

    @Column(nullable = false)
    var content: String = "내용 없음"
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", nullable = false)
    lateinit var author: Users


    protected constructor() : this(null)
    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }

    companion object {
        fun create(title: String, content: String, author: Users): Blog {
            return Blog().apply {
                this.title = title
                this.content = content
                this.author = author
            }
        }

    }



}
