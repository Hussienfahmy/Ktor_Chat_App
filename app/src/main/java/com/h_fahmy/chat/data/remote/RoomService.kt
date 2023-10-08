package com.h_fahmy.chat.data.remote

import com.h_fahmy.chat.domain.model.Room

interface RoomService {
    suspend fun createRoom(name: String) : Result<Unit>

    suspend fun getAllRooms(): List<Room>

    sealed class EndPoints(val url: String) {
        class CreateRoom(name: String) : EndPoints("${Constants.BASE_URL}/create-room/$name")
        object GetAllRooms : EndPoints("${Constants.BASE_URL}/rooms")
    }
}