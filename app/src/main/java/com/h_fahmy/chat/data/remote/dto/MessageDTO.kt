package com.h_fahmy.chat.data.remote.dto

import com.h_fahmy.chat.domain.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class MessageDTO(
    val text: String,
    val timestamp: Long,
    val userName: String,
    val id: String
) {
    fun toMessage(): Message {
        val date = Date(timestamp)
        val formattedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)

        return Message(
            text = text,
            formattedTime = formattedDate,
            userName = userName,
        )
    }
}