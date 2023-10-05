package com.h_fahmy.chat.domain.model

data class Message(
    val text: String,
    val formattedTime: String,
    val userName: String,
)
