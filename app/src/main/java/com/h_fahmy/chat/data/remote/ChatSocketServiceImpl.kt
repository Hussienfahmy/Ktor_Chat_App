package com.h_fahmy.chat.data.remote

import android.util.Log
import com.h_fahmy.chat.data.remote.dto.MessageDTO
import com.h_fahmy.chat.domain.model.Message
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json

private const val TAG = "ChatSocketServiceImpl"

class ChatSocketServiceImpl(
    private val client: HttpClient
) : ChatSocketService {

    private var socket: WebSocketSession? = null

    override suspend fun initSession(userName: String, roomId: String): Result<Unit> {
        return try {
            socket = client.webSocketSession {
                url(ChatSocketService.EndPoints.ChatSocketRoute(userName, roomId).url)
                headers {
                    accept(ContentType.Application.Json)
                }
            }
            if (socket?.isActive == true) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Couldn't connect to the server"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        } catch (e: Exception) {
            Log.e(TAG, "sendMessage: ", e)
        }
    }

    override fun observeMessages(): Flow<Message> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as Frame.Text).readText()
                    val messageDTO = Json.decodeFromString<MessageDTO>(json)
                    messageDTO.toMessage()
                } ?: emptyFlow()
        } catch (e: Exception) {
            Log.e(TAG, "observeMessages: ", e)
            emptyFlow()
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}