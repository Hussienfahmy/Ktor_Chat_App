package com.h_fahmy.chat.data.remote

import android.util.Log
import com.h_fahmy.chat.data.remote.dto.MessageDTO
import com.h_fahmy.chat.domain.model.Message
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val TAG = "MessageServiceImpl"
class MessageServiceImpl(
    private val client : HttpClient,
): MessageService {

    override suspend fun getAllMessages(): List<Message> {
        return try {
            client.get(MessageService.EndPoints.GetAllMessages.url)
                .body<List<MessageDTO>>()
                .map { it.toMessage() }
        }catch (e: Exception) {
            Log.e(TAG, "getAllMessages: ", e)
            emptyList()
        }
    }
}