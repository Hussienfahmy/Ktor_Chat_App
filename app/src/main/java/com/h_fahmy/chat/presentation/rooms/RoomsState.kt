package com.h_fahmy.chat.presentation.rooms

import com.h_fahmy.chat.domain.model.Room

data class RoomsState(
    val rooms: List<Room> = emptyList(),
    val isLoading: Boolean = false,
    val userName: String = "",
)
