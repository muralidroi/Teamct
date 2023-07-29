package com.murali.teamct.presentation.chat

import com.murali.teamct.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
