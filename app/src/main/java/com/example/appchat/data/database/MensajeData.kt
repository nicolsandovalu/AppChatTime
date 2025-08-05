package com.example.appchat.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appchat.domain.model.Mensaje.MessageStatus

@Entity
data class MensajeData(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contenido: String,
    val estado: String,
    val estado1: EstadoMensaje
)