package com.h_fahmy.chat.di

import com.h_fahmy.chat.data.remote.ChatSocketService
import com.h_fahmy.chat.data.remote.ChatSocketServiceImpl
import com.h_fahmy.chat.data.remote.MessageService
import com.h_fahmy.chat.data.remote.MessageServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Provides
    @Singleton
    fun provideMessageService(httpClient: HttpClient): MessageService {
        return MessageServiceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideChatSocketService(httpClient: HttpClient): ChatSocketService {
        return ChatSocketServiceImpl(httpClient)
    }
}