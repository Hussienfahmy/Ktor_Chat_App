package com.h_fahmy.chat.presentation.chat

import com.h_fahmy.chat.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
)