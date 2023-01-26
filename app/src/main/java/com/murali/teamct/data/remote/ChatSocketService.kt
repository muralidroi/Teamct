package com.murali.teamct.data.remote

import com.murali.teamct.domain.model.Message
import com.murali.teamct.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {
    suspend fun initSession(
        userName: String
    ) : Resource<Unit>

    suspend fun sendMessage(message: String)

    fun observeMessages(): Flow<Message>

    suspend fun closeSession()

    companion object {
        const val baseUrl = "ws://192.168.1.8:8080"
    }

    sealed class EndPoints(val url : String) {
        object ChatSocket : EndPoints("$baseUrl/chat-socket")
    }
}