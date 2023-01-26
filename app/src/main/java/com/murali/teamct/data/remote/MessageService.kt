package com.murali.teamct.data.remote

import com.murali.teamct.domain.model.Message

interface MessageService {
    suspend fun getAllMessage() : List<Message>

    companion object {
        const val baseUrl = "http://192.168.1.8:8080"
    }

    sealed class EndPoints(val url : String) {
        object GetAllMessages : EndPoints("$baseUrl/messages")
    }
}