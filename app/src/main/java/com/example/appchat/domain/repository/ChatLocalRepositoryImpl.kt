package com.example.appchat.domain.repository

import com.example.appchat.data.database.dao.MensajeDao
import com.example.appchat.data.database.entity.MensajeEntity
import com.example.appchat.domain.model.Mensaje
import javax.inject.Inject

class ChatLocalRepositoryImpl @Inject constructor(
    private val dao: MensajeDao
) : ChatLocalRepository {

    override suspend fun obtenerMensajes(salaId: String): List<Mensaje> {
        return dao.getMensajesPorSala(salaId).map {
            Mensaje(it.contenido, it.remitente, it.timestamp)
        }
    }

    override suspend fun guardarMensaje(mensaje: Mensaje, salaId: String) {
        dao.insertarMensaje(
            MensajeEntity(
                contenido = mensaje.contenido,
                remitente = mensaje.remitente,
                timestamp = mensaje.timestamp,
                salaId = salaId
            )
        )
    }
}
