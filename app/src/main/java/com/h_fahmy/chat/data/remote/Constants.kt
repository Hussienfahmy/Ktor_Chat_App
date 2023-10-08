package com.h_fahmy.chat.data.remote

import com.h_fahmy.chat.BuildConfig

object Constants {
    const val BASE_URL =
        "${BuildConfig.HttpProtocol}://${BuildConfig.ServerIP}:${BuildConfig.ServerPort}"
    const val SOCKET_BASE_URL = "ws://${BuildConfig.ServerIP}:${BuildConfig.ServerPort}"
}