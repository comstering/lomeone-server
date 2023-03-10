package com.lomeone.domain.memory.usecase

import com.lomeone.domain.memory.entity.Photo
import com.lomeone.domain.memory.repository.PhotoRepository
import com.lomeone.domain.memory.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.validation.constraints.NotBlank

@Service
class CreatePhoto(
    private val photoRepository: PhotoRepository,
    private val postRepository: PostRepository
) {
    @Transactional
    fun execute(request: CreatePhotoRequest): CreatePhotoResponse {
        val (postId, url) = request

        val post = getPost(postId)

        val photo = photoRepository.save(
            Photo(
                post = post,
                url = url
            )
        )

        return CreatePhotoResponse(
            id = photo.id,
            url = photo.url
        )
    }

    private fun getPost(postId: Long) =
        postRepository.findByIdOrNull(postId) ?: throw Exception("Post not found")
}

data class CreatePhotoRequest(
    val postId: Long,
    @field:NotBlank val url: String
)

data class CreatePhotoResponse(
    val id: Long,
    val url: String
)
