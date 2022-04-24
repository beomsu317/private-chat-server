package com.beomsu317.data.user

import com.beomsu317.use_case.user.UserRepository
import com.beomsu317.entity.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.toId

class UserRepositoryImpl(
    private val db: CoroutineDatabase,
    private val dispatcher: CoroutineDispatcher
): UserRepository {

    private val users = db.getCollection<User>()

    override suspend fun insertUser(user: User): Boolean {
        return withContext(dispatcher) {
            users.insertOne(user).wasAcknowledged()
        }
    }

    override suspend fun getUserById(id: Id<User>): User? {
        return withContext(dispatcher) {
            users.findOne(User::id eq id)
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return withContext(dispatcher) {
            users.findOne(User::email eq email)
        }
    }

    override suspend fun updateUser(user: User) {
        return withContext(dispatcher) {
            users.updateOne(User::id eq user.id, user).wasAcknowledged()
        }
    }
}