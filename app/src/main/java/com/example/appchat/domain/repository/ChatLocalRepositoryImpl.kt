package com.example.appchat.domain.repository

import com.example.appchat.data.database.dao.MensajeDao
import com.example.appchat.domain.model.Mensaje
import com.example.appchat.domain.model.Mensaje.MessageStatus
import kotlinx.coroutines.flow.Flow // Importa Flow
import javax.inject.Inject

class ChatLocalRepositoryImpl @Inject constructor(

    override val dao: MensajeDao
) : ChatLocalRepository {

    override fun obtenerMensajes(salaId: String): Flow<List<Mensaje>> {
        return dao.getMessagesForRoom(salaId)
    }

    override suspend fun guardarMensaje(mensaje: Mensaje, salaId: String) {
        val mensajeConSalaId = mensaje.copy(roomId = salaId)
        dao.insertMessage(mensajeConSalaId)
    }

    override suspend fun actualizarEstado(timestamp: Long, status: MessageStatus) {
        dao.updateMessageStatus(timestamp, status.name)
    }
}