package com.h_fahmy.chat.data.remote

import com.h_fahmy.chat.BuildConfig
import com.h_fahmy.chat.domain.model.Message

interface MessageService {

    suspend fun getAllMessages(): List<Message>

    companion object {
        private const val BASE_URL = "${BuildConfig.HttpProtocol}://${BuildConfig.ServerIP}:${BuildConfig.ServerPort}"
    }

    sealed class EndPoints(val url: String) {
        object GetAllMessages : EndPoints("$BASE_URL/messages")
    }
}