package com.h_fahmy.chat.data.remote

import com.h_fahmy.chat.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(
        userName: String,
        roomId: String,
    ): Result<Unit>

    suspend fun sendMessage(message: String)

    fun observeMessages(): Flow<Message>

    suspend fun closeSession()

    sealed class EndPoints(val url: String) {
        class ChatSocketRoute(userName: String, roomId: String) :
            EndPoints("${Constants.SOCKET_BASE_URL}/chat-socket?username=$userName&roomId=$roomId")
    }
}