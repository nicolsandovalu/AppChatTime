package com.example.appchat.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "mensajes")
data class Mensaje(
    @PrimaryKey val id: String = UUID.randomUUID()
        .toString(), //id único del mensaje
    val roomId: String,
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val type: MessageType,
    val fileUrl: String? = null,
    val status: MessageStatus
) {

    enum class MessageType {
        TEXT, IMAGE, FILE
    }

    enum class MessageStatus {
        SENT, DELIVERED, READ
    }

    constructor() : this("", "", "", "", 0L, MessageType.TEXT, null, null, MessageStatus.SENT)
}
