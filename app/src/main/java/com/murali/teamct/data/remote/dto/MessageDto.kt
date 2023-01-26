package com.murali.teamct.data.remote.dto

import com.murali.teamct.domain.model.Message
import java.text.DateFormat
import java.util.*

@kotlinx.serialization.Serializable
data class MessageDto(
    val text: String,
    val timeStamp: Long,
    val userName: String,
    val id: String
) {
    fun toMessage() : Message {
        val date = Date(timeStamp)
        val formattedDate = DateFormat.
        getDateInstance(DateFormat.DEFAULT)
            .format(date)
        return Message(
            text = text,
            formattedTime = formattedDate,
            userName = userName
        )
    }
}
