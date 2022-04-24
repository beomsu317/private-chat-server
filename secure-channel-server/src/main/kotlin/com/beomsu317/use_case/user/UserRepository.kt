package com.beomsu317.use_case.user

import com.beomsu317.entity.User


interface UserRepository {

    suspend fun insertUser(user: User): Boolean

    suspend fun getUserByEmail(email: String): User?

    suspend fun updateUser(user: User)
}