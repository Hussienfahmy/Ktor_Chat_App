package com.h_fahmy.chat.presentation.rooms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.h_fahmy.chat.data.remote.RoomService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val roomService: RoomService,
) : ViewModel() {

    var state by mutableStateOf(RoomsState())
        private set

    init {
        refreshRooms()
    }

    private val _onJoinChat = MutableSharedFlow<String>()
    val onJoinChat = _onJoinChat.asSharedFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun refreshRooms() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val rooms = roomService.getAllRooms()
            state = state.copy(rooms = rooms, isLoading = false)
        }
    }

    fun onUsernameChanged(newUsername: String) {
        state = state.copy(userName = newUsername)
    }

    fun onJoinClicked(roomId: String) {
        viewModelScope.launch {
            if (state.userName.isNotBlank()) {
                _onJoinChat.emit("$roomId/${state.userName}")
            } else {
                _toastEvent.emit("Please enter a username")
            }
        }
    }

    fun createRoom(roomName: String){
        viewModelScope.launch {
            roomService.createRoom(roomName)
        }
    }
}