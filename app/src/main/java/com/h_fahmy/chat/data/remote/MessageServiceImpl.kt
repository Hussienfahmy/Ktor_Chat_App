package com.h_fahmy.chat.data.remote

import android.util.Log
import com.h_fahmy.chat.data.remote.dto.ChatDto
import com.h_fahmy.chat.domain.model.Message
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType

private const val TAG = "MessageServiceImpl"
class MessageServiceImpl(
    private val client : HttpClient,
): MessageService {

    override suspend fun getAllMessages(roomId: String): List<Message> {
        return try {
            client.get(MessageService.EndPoints.GetAllMessages(roomId).url) {
                headers {
                    accept(ContentType.Application.Json)
                }
            }.body<ChatDto>()
                .let { chatDto ->
                    chatDto.messages.map { it.toMessage() }
                }
        }catch (e: Exception) {
            Log.e(TAG, "getAllMessages: ", e)
            emptyList()
        }
    }
}