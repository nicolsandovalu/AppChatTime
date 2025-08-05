package com.example.appchat.domain.repository

import com.example.appchat.data.database.dao.MensajeDao
import com.example.appchat.domain.model.Mensaje
import kotlinx.coroutines.flow.Flow

interface ChatLocalRepository {
    val dao: MensajeDao

    fun obtenerMensajes(salaId: String): Flow<List<Mensaje>>
    suspend fun guardarMensaje(mensaje: Mensaje, salaId: String)
    suspend fun actualizarEstado(timestamp: Long, status: Mensaje.MessageStatus)

}