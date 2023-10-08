package com.h_fahmy.chat.data.remote

import com.h_fahmy.chat.domain.model.Message

interface MessageService {

    suspend fun getAllMessages(roomId: String): List<Message>

    sealed class EndPoints(val url: String) {
        class GetAllMessages(roomId: String) : EndPoints("${Constants.BASE_URL}/messages/$roomId")
    }
}