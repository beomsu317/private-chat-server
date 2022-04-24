package com.beomsu317.use_case.chat.repository

import com.beomsu317.entity.Message

interface MessageRepository {

    suspend fun insertMessage(message: Message): Boolean
}