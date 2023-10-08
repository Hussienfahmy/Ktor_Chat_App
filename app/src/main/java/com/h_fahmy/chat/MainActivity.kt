package com.h_fahmy.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.h_fahmy.chat.presentation.chat.ChatScreen
import com.h_fahmy.chat.presentation.username.RoomsScreen
import com.h_fahmy.chat.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme(darkTheme = false) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "username_screen"
                ) {
                    composable("username_screen") {
                        RoomsScreen(onNavigate = navController::navigate)
                    }

                    composable(
                        route = "chat_screen/{roomId}/{username}",
                        arguments = listOf(
                            navArgument(name = "username") {
                                type = NavType.StringType
                                nullable = true
                            },
                            navArgument(name = "roomId") {
                                type = NavType.StringType
                                nullable = false
                            }
                        )
                    ) { backStackEntry ->
                        val username = backStackEntry.arguments?.getString("username")
                        ChatScreen(username = username)
                    }
                }
            }
        }
    }
}