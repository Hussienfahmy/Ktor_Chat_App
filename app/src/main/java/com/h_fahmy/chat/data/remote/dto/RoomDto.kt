package com.h_fahmy.chat.data.remote.dto

import com.h_fahmy.chat.domain.model.Room
import kotlinx.serialization.Serializable

@Serializable
data class RoomDto(
    val name: String,
    val activeMembers: List<String>,
    val _id: String,
) {
    fun toRoom() = Room(
        name = name,
        activeMembers = activeMembers.size,
        id = _id,
    )
}
