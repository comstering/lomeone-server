package io.github.comstering.domain.memory.usecase

import io.github.comstering.domain.memory.entity.Photo
import io.github.comstering.domain.memory.entity.Post
import io.github.comstering.domain.memory.repository.PhotoRepository
import io.github.comstering.domain.memory.repository.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull

class CreatePhotoTest : BehaviorSpec({
    val photoRepository: PhotoRepository = mockk()
    val postRepository: PostRepository = mockk()
    val createPhoto = CreatePhoto(photoRepository, postRepository)

    Given("사진의 url이 공백이 아니고") {
        val urlInput = "url"
        And("포스트가 존재하면") {
            val post = mockk<Post>()
            every { postRepository.findByIdOrNull(any()) } returns post
            When("해당 포스트에 사진을 추가할 때") {
                val request = CreatePhotoRequest(
                    postId = 1,
                    url = urlInput,
                    isMain = true
                )

                every { photoRepository.save(any()) } returns Photo(
                    id = 1L,
                    url = urlInput,
                    post = post,
                    isMain = true
                )

                val response = withContext(Dispatchers.IO) {
                    createPhoto.execute(request)
                }
                Then("사진이 추가된다") {
                    response.id shouldBe 1L
                    response.url shouldBe urlInput
                    response.isMain shouldBe true
                }
            }
        }
    }
})
