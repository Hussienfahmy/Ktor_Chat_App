package com.h_fahmy.chat.data.remote

import com.h_fahmy.chat.BuildConfig
import com.h_fahmy.chat.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(
        userName: String
    ): Result<Unit>

    suspend fun sendMessage(message: String)

    fun observeMessages(): Flow<Message>

    suspend fun closeSession()

    companion object {
        private const val BASE_URL = "ws://${BuildConfig.ServerIP}:${BuildConfig.ServerPort}"
    }

    sealed class EndPoints(val url: String) {
        object ChatSocketRoute : EndPoints("${BASE_URL}/chat-socket")
    }
}