package com.lomeone.domain.user.usecase

import com.lomeone.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class UpdateUserInfo(
    private val userRepository: UserRepository
) {
    @Transactional
    fun execute(request: UpdateUserInfoServiceRequest): UpdateUserInfoServiceResponse {
        val (userToken, name, nickname, birthday, photoUrl) = request

        val user = getUser(userToken)

        user.updateUserInfo(name, nickname, birthday, photoUrl)

        return UpdateUserInfoServiceResponse(
            userToken = user.userToken,
            name = user.name,
            nickname = user.nickname,
            photoUrl = user.photoUrl,
            birthday = user.birthday
        )
    }

    private fun getUser(userToken: String) = userRepository.findByUserToken(userToken)
        ?: throw Exception("User not found")
}

data class UpdateUserInfoServiceRequest(
    val userToken: String,
    val name: String,
    val nickname: String,
    val birthday: ZonedDateTime,
    val photoUrl: String
)

data class UpdateUserInfoServiceResponse(
    val userToken: String,
    val name: String,
    val nickname: String,
    val birthday: ZonedDateTime,
    val photoUrl: String
)
