package com.beomsu317.data.message

import com.beomsu317.entity.Message
import com.beomsu317.use_case.chat.repository.MessageRepository
import com.mongodb.client.model.changestream.FullDocument
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessageRepositoryImpl(
    private val db: CoroutineDatabase,
    private val dispatcher: CoroutineDispatcher
): MessageRepository {

    private val messages = db.getCollection<Message>()

    override suspend fun insertMessage(message: Message): Boolean {
        return withContext(dispatcher) {
            messages.watch<Message>().consumeEach {
                println(it)
            }

            messages.insertOne(message).wasAcknowledged()
        }
    }
}