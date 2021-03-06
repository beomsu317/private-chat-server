package com.beomsu317.use_case.user

import com.beomsu317.entity.User
import org.litote.kmongo.Id


interface UserRepository {

    suspend fun insertUser(user: User): Boolean

    suspend fun getUserById(id: String): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun updateUser(user: User)

    suspend fun getUsers(): List<User>

    suspend fun getUsers(searchText: String): List<User>
}