package com.h_fahmy.chat.presentation.username

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsernameViewModel @Inject constructor(

) : ViewModel() {

    var usernameText by mutableStateOf("")
        private set

    private val _onJoinChat = MutableSharedFlow<String>()
    val onJoinChat = _onJoinChat.asSharedFlow()

    fun onUsernameChanged(newUsername: String) {
        usernameText = newUsername
    }

    fun onJoinClicked() {
        viewModelScope.launch {
            if (usernameText.isNotBlank()) {
                _onJoinChat.emit(usernameText)
            }
        }
    }
}