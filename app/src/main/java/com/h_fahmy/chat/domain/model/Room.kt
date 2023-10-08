package com.h_fahmy.chat.domain.model

data class Room(
    val id: String,
    val name: String,
    val activeMembers: Int,
)