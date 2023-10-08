package com.h_fahmy.chat.data.remote

import com.h_fahmy.chat.domain.model.Message

interface MessageService {

    suspend fun getAllMessages(): List<Message>

    sealed class EndPoints(val url: String) {
        object GetAllMessages : EndPoints("${Constants.BASE_URL}/messages")
    }
}