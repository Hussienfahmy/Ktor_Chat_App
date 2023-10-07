package com.h_fahmy.chat.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.h_fahmy.chat.domain.model.Message
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    username: String?,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.toastEvent.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnect()
            } else if (event == Lifecycle.Event.ON_START) {
                viewModel.connectToChat()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true,
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            items(state.messages) { message ->
                val isOwn = message.username == username
                Box(
                    contentAlignment = if (isOwn) Alignment.CenterEnd else Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MessageItem(
                        message = message,
                        isOwn = isOwn,
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = viewModel.messageText,
                onValueChange = viewModel::onMessageChanged,
                placeholder = {
                    Text(text = "Enter a message...")
                },
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = viewModel::sendMessage) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "send")
            }
        }
    }
}

@Composable
fun MessageItem(
    message: Message,
    isOwn: Boolean,
) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .drawBehind {
                val cornerRadius = 10.dp.toPx()
                val triangleHeight = 20.dp.toPx()
                val triangleWidth = 25.dp.toPx()
                val trianglePath = Path().apply {
                    if (isOwn) {
                        moveTo(size.width, size.height - cornerRadius)
                        lineTo(size.width, size.height + triangleHeight)
                        lineTo(size.width - triangleWidth, size.height - cornerRadius)
                        close()
                    } else {
                        moveTo(0f, size.height - cornerRadius)
                        lineTo(0f, size.height + triangleHeight)
                        lineTo(triangleWidth, size.height - cornerRadius)
                        close()
                    }
                }
                drawPath(path = trianglePath, color = if (isOwn) Color.Green else Color.DarkGray)
            }
            .background(
                color = if (isOwn) Color.Green else Color.DarkGray,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp)
    ) {
        Text(text = message.username, fontWeight = FontWeight.Bold, color = Color.White)

        Text(text = message.text, color = Color.White)

        Text(
            text = message.formattedTime,
            color = Color.White,
            modifier = Modifier.align(Alignment.End)
        )
    }
}
