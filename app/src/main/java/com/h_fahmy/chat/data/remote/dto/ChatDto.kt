package com.h_fahmy.chat.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val messages: List<MessageDTO>,
    val roomId: String
)