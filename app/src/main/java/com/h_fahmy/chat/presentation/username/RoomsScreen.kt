package com.h_fahmy.chat.presentation.username

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomsScreen(
    viewModel: RoomsViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.onJoinChat.collectLatest { roomAndUsername ->
            onNavigate("chat_screen/$roomAndUsername")
        }
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.toastEvent.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        TextField(
            value = state.userName,
            onValueChange = viewModel::onUsernameChanged,
            placeholder = {
                Text(text = "Enter a username...")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = viewModel::refreshRooms) {
            Text(text = "Refresh Rooms")
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(state.rooms, key = { it.id }) { room ->
                Card(){
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = room.name, style = MaterialTheme.typography.titleMedium)

                            Text(
                                text = "Online: ${room.activeMembers}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        TextButton(onClick = { viewModel.onJoinClicked(room.id) }) {
                            Text(text = "Join")
                        }
                    }
                }
            }
        }
    }
}