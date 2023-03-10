package com.lomeone.domain.memory.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Photo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,
    val url: String
) {
    var deleted: Boolean = false
        protected set

    init {
        ensureUrlIsNotBlank(this.url)
    }

    private fun ensureUrlIsNotBlank(url: String) {
        url.isBlank() && throw IllegalArgumentException("url must not be blank")
    }

    fun delete() {
        this.deleted = true
    }

    fun restore() {
        this.deleted = false
    }
}
