package com.example.appchat.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mensajes")
data class MensajeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val contenido: String,
    val remitente: String,
    val timestamp: Long,
    val salaId: String,
    val estado: String = "ENVIADO"
)