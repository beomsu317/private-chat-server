package com.beomsu317.use_case.user

import com.beomsu317.entity.User
import org.litote.kmongo.Id

class AddFriendsUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke() {

    }
}

@kotlinx.serialization.Serializable
data class AddFriendsRequest(
    val friends: List<String>
)