package com.example.appchat.data.repository

import com.example.appchat.data.datasource.websocket.ChatWebSocketClient
import com.example.appchat.domain.model.Mensaje
import com.example.appchat.domain.repository.ChatLocalRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow // Importar Flow
import com.example.appchat.domain.model.Mensaje.MessageStatus // Importar MessageStatus
import com.example.appchat.domain.repository.ChatRepository
import com.example.appchat.utils.Constants

class ChatRepositoryImpl @Inject constructor(
    private val webSocketClient: ChatWebSocketClient
) : ChatRepository {

    override fun conectar(salaId: String, onMensaje: (String) -> Unit) {
        // Aquí conectas el WebSocket. Asegúrate de que Constants.WEBSOCKET_URL sea la URL correcta.
        // Podrías necesitar ajustar la URL para incluir el salaId si tu servidor lo requiere.
        webSocketClient.connect(Constants.WEBSOCKET_URL, object : ChatWebSocketClient.ChatWebSocketListener() {
            override fun onNuevoMensaje(mensaje: String) {
                onMensaje(mensaje)
            }
            // Puedes añadir más overrides de WebSocketListener si es necesario, como onOpen, onClose, etc.
        })
    }

    override fun enviar(mensaje: String) {
        webSocketClient.sendMenssage(mensaje)
    }

    override fun cerrar() {
        webSocketClient.close()
    }
}

