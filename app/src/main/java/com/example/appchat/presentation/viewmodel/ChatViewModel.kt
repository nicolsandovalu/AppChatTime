package com.example.appchat.presentation.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.appchat.domain.model.Mensaje
import com.example.appchat.domain.repository.ChatLocalRepository
import com.example.appchat.domain.usecase.ConectarChatUseCase
import com.example.appchat.domain.usecase.EnviarMensajeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(

    private val conectar: ConectarChatUseCase,
    private val enviar: EnviarMensajeUseCase,
    private val chatLocal: ChatLocalRepository
) : ViewModel() {

    private val _mensajes = MutableLiveData<MutableList<String>>(mutableListOf())
    val mensajes: LiveData<MutableList<String>> = _mensajes

    fun conectarWebSocket(salaId: String) {

        viewModelScope.launch {
            cargarMensajesLocalmente(salaId)

            conectar.conectar(salaId) { nuevoMensajeContent ->
                viewModelScope.launch {
                    val nuevoMensaje = Mensaje(
                        content = nuevoMensajeContent,
                        senderId = "otro", //O el ID real del remitente
                        timestamp = System.currentTimeMillis(),
                        roomId = salaId,
                        type = Mensaje.MessageType.TEXT, //asume TEXT por defecto
                        status = Mensaje.MessageStatus.SENT //asume sent por defecto
                    )

                    _mensajes.value?.add("${nuevoMensaje.senderId}: ${nuevoMensaje.content} (${nuevoMensaje.status.name.lowercase()}")
                    _mensajes.postValue(_mensajes.value)

                    chatLocal.guardarMensaje(nuevoMensaje, salaId)
                }
            }
        }
    }

    fun enviarMensaje(texto: String) {
        enviar.enviar(texto)
        val mensajeEnviado = Mensaje (
            content = texto,
            senderId = "Yo",
            timestamp = System.currentTimeMillis(),
            roomId = "current_room_id",
            type = Mensaje.MessageType.TEXT,
            status = Mensaje.MessageStatus.SENT
        )

        _mensajes.value?.add("${mensajeEnviado.senderId}: ${mensajeEnviado.content} (${mensajeEnviado.status.name.lowercase()}")
        _mensajes.postValue(_mensajes.value)

        viewModelScope.launch {
            chatLocal.guardarMensaje(mensajeEnviado, mensajeEnviado.roomId)
        }
    }

    private suspend fun cargarMensajesLocalmente(salaId: String) {
        try {
            // Recopila el Flow emitido por el repositorio
            chatLocal.obtenerMensajes(salaId).collect { mensajesList ->
                _mensajes.postValue(mensajesList.map {
                    "${it.senderId}: ${it.content} (${it.status.name.lowercase()})"
                }.toMutableList())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _mensajes.postValue(mutableListOf())
        }
    }
}