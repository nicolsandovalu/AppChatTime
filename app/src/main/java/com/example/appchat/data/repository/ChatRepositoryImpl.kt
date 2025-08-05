package com.example.appchat.data.repository

import com.example.appchat.data.database.dao.MensajeDao
import com.example.appchat.domain.model.Mensaje
import com.example.appchat.domain.repository.ChatLocalRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow // Importar Flow
import com.example.appchat.domain.model.Mensaje.MessageStatus // Importar MessageStatus

class ChatRepositoryImpl @Inject constructor(
    override val dao: MensajeDao // 'dao' es la propiedad abstracta de ChatLocalRepository
) : ChatLocalRepository {

    // Se actualiza la firma para que coincida con la interfaz: retorna Flow<List<Mensaje>>
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
