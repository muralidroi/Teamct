package com.murali.teamct.data.remote

import com.murali.teamct.data.remote.dto.MessageDto
import com.murali.teamct.domain.model.Message
import io.ktor.client.*
import io.ktor.client.request.*

class MessageServiceImpl(
    private val client: HttpClient
): MessageService {
    override suspend fun getAllMessage(): List<Message> {
        return try {
            client.get<List<MessageDto>>(MessageService.EndPoints.GetAllMessages.url)
                .map { it.toMessage() }
        } catch (ex : Exception) {
            emptyList()
        }
    }
}