package com.murali.teamct.data.remote

import com.murali.teamct.data.remote.dto.MessageDto
import com.murali.teamct.domain.model.Message
import com.murali.teamct.util.Resource
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ChatSocketServiceImpl(
    private val client: HttpClient
): ChatSocketService {

    private var socket : WebSocketSession? = null

    override suspend fun initSession(userName: String): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url(ChatSocketService.EndPoints.ChatSocket.url)
            }
            if(socket?.isActive == true) {
                Resource.Success(Unit)
            } else
                Resource.Error("Couldn't Establish the Connection")
        } catch (ex : Exception) {
            ex.printStackTrace()
            Resource.Error(ex.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun observeMessages(): Flow<Message> {
        return try {
            socket?.incoming?.receiveAsFlow()?.filter { it is Frame.Text }?.map {
                val json = (it as? Frame.Text)?.readText() ?: ""
                val messageDto = Json.decodeFromString<MessageDto>(json)
                messageDto.toMessage()
            } ?: flow {  }
        } catch (ex : Exception) {
            ex.printStackTrace()
            flow {  }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}