package com.h_fahmy.chat.data.remote

import android.util.Log
import com.h_fahmy.chat.data.remote.dto.RoomDto
import com.h_fahmy.chat.domain.model.Room
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url

private const val TAG = "RoomServiceImpl"
class RoomServiceImpl(
    private val client: HttpClient,
) : RoomService {

    override suspend fun createRoom(name: String): Result<Unit> {
        return try {
            client.post {
                url(RoomService.EndPoints.CreateRoom(name).url)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "createRoom: ", e)
            Result.failure(e)
        }
    }

    override suspend fun getAllRooms(): List<Room> {
        return client.get {
            url(RoomService.EndPoints.GetAllRooms.url)
        }.body<List<RoomDto>>()
            .map { it.toRoom() }
    }
}