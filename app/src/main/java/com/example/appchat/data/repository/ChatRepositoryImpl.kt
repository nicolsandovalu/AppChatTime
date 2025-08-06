package com.example.appchat.data.repository

import com.example.appchat.data.datasource.websocket.ChatWebSocketClient
import com.example.appchat.domain.repository.ChatRepository
import com.example.appchat.utils.Constants
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val webSocketClient: ChatWebSocketClient
) : ChatRepository {

    override fun conectar(salaId: String, onMensaje: (String) -> Unit) {
        webSocketClient.connect(Constants.WEBSOCKET_URL, object : ChatWebSocketClient.ChatWebSocketListener() {
            override fun onNuevoMensaje(mensaje: String) {
                onMensaje(mensaje)
            }
        })
    }

    override fun enviar(mensaje: String) {
        webSocketClient.sendMenssage(mensaje)
    }

    override fun cerrar() {
        webSocketClient.close()
    }
}