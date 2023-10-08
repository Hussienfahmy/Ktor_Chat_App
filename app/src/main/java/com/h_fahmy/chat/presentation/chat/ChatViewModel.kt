package com.h_fahmy.chat.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.h_fahmy.chat.data.remote.ChatSocketService
import com.h_fahmy.chat.data.remote.MessageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageService: MessageService,
    private val chatSocketService: ChatSocketService,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var messageText by mutableStateOf("")
        private set

    var state by mutableStateOf(ChatState())
        private set

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    private val username get() = savedStateHandle.get<String>("username").orEmpty()
    private val roomId get() = savedStateHandle.get<String>("roomId").orEmpty()

    fun connectToChat() {
        getAllMessages()

        viewModelScope.launch {
            val result = chatSocketService.initSession(username, roomId)
            result.onFailure {
                _toastEvent.emit(it.message ?: "Unknown error")
            }.onSuccess {
                chatSocketService.observeMessages()
                    .onEach { message ->
                        val newList = state.messages.toMutableList()
                            .apply {
                                add(0, message)
                            }
                        state = state.copy(messages = newList)
                    }.launchIn(viewModelScope)
            }
        }
    }

    fun onMessageChanged(newMessage: String) {
        messageText = newMessage
    }

    fun disconnect() {
        viewModelScope.launch {
            chatSocketService.closeSession()
        }
    }

    fun getAllMessages() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = messageService.getAllMessages(roomId)
            state = state.copy(isLoading = false, messages = result)
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            if (messageText.isNotBlank()) {
                chatSocketService.sendMessage(messageText)
                messageText = ""
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}