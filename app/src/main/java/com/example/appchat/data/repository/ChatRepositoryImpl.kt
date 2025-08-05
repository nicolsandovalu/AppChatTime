package com.example.appchat.data.repository

import com.example.appchat.data.database.MensajeData
import com.example.appchat.data.database.dao.MensajeDao
import com.example.appchat.data.database.entity.MensajeEntity
import com.example.appchat.domain.model.EstadoMensaje
import com.example.appchat.domain.repository.ChatLocalRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    override val dao: MensajeDao
) : ChatLocalRepository {

    override suspend fun obtenerMensajes(salaId: String): List<MensajeData> {
        return dao.getMensajesPorSala(salaId).map {
            MensajeData(
                contenido = it.contenido,
                remitente = it.remitente,
                timestamp = it.timestamp,
                estado = if (it.estado.isNotEmpty()) {
                    try {
                        EstadoMensaje.valueOf(it.estado)
                    } catch (e: IllegalArgumentException) {
                        EstadoMensaje.ENVIADO
                    }
                } else {
                    EstadoMensaje.ENVIADO
                }
            )
        }
    }

    override suspend fun guardarMensaje(mensaje: MensajeData, salaId: String) {
        dao.insertarMensaje(
            MensajeEntity(
                contenido = mensaje.contenido,
                remitente = mensaje.remitente,
                timestamp = mensaje.timestamp,
                salaId = salaId,
                estado = mensaje.estado.name
            )
        )
    }

    override suspend fun actualizarEstado(timestamp: Long, estado: EstadoMensaje) {
        dao.actualizarEstadoMensaje(timestamp, estado.name)
    }
}