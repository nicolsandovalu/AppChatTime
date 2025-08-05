package com.example.appchat.domain.repository

import com.example.appchat.data.database.dao.MensajeDao
import com.example.appchat.domain.model.Mensaje
import com.example.appchat.domain.model.Mensaje.MessageStatus
import kotlinx.coroutines.flow.Flow // Importa Flow
import javax.inject.Inject

class ChatLocalRepositoryImpl @Inject constructor(

    private val mensajeDao: MensajeDao
) : ChatLocalRepository {

    override val dao: MensajeDao //implementación de la propiedad abstracta 'dao'
        get() = mensajeDao

    override fun obtenerMensajes(salaId: String): Flow<List<Mensaje>> {
        return mensajeDao.getMessagesForRoom(salaId)
        }

    override suspend fun guardarMensaje(mensaje: Mensaje, salaId: String) {
        val mensajeConSalaId = mensaje.copy(roomId = salaId)
        mensajeDao.insertMessage(mensajeConSalaId)
    }

    override suspend fun actualizarEstado(timestamp: Long, status: MessageStatus) {
        mensajeDao.updateMessageStatus(timestamp, status.name)
    }

}