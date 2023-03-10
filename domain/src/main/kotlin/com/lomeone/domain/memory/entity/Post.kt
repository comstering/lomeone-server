package com.lomeone.domain.memory.entity

import com.lomeone.domain.common.entity.AuditEntity
import com.lomeone.domain.user.entity.User
import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    title: String,
    content: String,
    visibility: Boolean,
    place: Place,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
) : AuditEntity() {
    var title: String = title
        protected set

    var content: String = content
        protected set

    var visibility: Boolean = visibility
        protected set

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "name", column = Column(name = "place_name")),
        AttributeOverride(name = "address", column = Column(name = "place_address"))
    )
    var place: Place = place
        protected set

    var deleted: Boolean = false
        protected set

    init {
        ensureTitleIsNotBlank(title)
        ensureContentIsNotBlank(content)
    }

    private fun ensureTitleIsNotBlank(title: String) {
        title.isBlank() && throw IllegalArgumentException("title must not be blank")
    }

    private fun ensureContentIsNotBlank(content: String) {
        content.isBlank() && throw IllegalArgumentException("content must not be blank")
    }

    override fun equals(other: Any?) = other is Post && other.id == this.id

    override fun hashCode() = this.id.hashCode()

    fun updatePost(title: String, content: String, visibility: Boolean, place: Place) {
        ensureTitleIsNotBlank(title)
        ensureContentIsNotBlank(content)
        this.title = title
        this.content = content
        this.visibility = visibility
        this.place = place
    }

    fun delete() {
        this.deleted = true
    }

    fun restore() {
        this.deleted = false
    }
}

@Embeddable
class Place(
    @Column
    val name: String,
    @Column
    val address: String
) {
    init {
        ensurePlaceNameIsNotBlank(name)
        ensureAddressIsNotBlank(address)
    }

    private fun ensurePlaceNameIsNotBlank(placeName: String) {
        placeName.isBlank() && throw IllegalArgumentException("placeName must not be blank")
    }

    private fun ensureAddressIsNotBlank(address: String) {
        address.isBlank() && throw IllegalArgumentException("address must not be blank")
    }
}
