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
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val conectar: ConectarChatUseCase,
    private val enviar: EnviarMensajeUseCase,
    private val chatLocal: ChatLocalRepository
) : ViewModel() {

    // Cambia el tipo de LiveData para que maneje objetos Mensaje
    private val _mensajes = MutableLiveData<MutableList<Mensaje>>(mutableListOf())
    val mensajes: LiveData<MutableList<Mensaje>> = _mensajes

    fun conectarWebSocket(salaId: String) {
        viewModelScope.launch {
            cargarMensajesLocalmente(salaId)

            conectar.conectar(salaId) { mensajeJson ->
                viewModelScope.launch {
                    val nuevoMensaje = Gson().fromJson(mensajeJson, Mensaje::class.java)

                    _mensajes.value?.add(nuevoMensaje)
                    _mensajes.postValue(_mensajes.value)

                    chatLocal.guardarMensaje(nuevoMensaje, salaId)
                }
            }
        }
    }

    fun enviarMensaje(texto: String) {
        val mensajeEnviado = Mensaje(
            content = texto,
            senderId = "Yo",
            timestamp = System.currentTimeMillis(),
            roomId = "current_room_id", // Debes obtener el ID real de la sala
            type = Mensaje.MessageType.TEXT,
            status = Mensaje.MessageStatus.SENT
        )

        // Agrega el mensaje a la lista local para mostrarlo inmediatamente
        _mensajes.value?.add(mensajeEnviado)
        _mensajes.postValue(_mensajes.value)

        enviar.enviar(texto) // Envía el mensaje real por el WebSocket

        viewModelScope.launch {
            chatLocal.guardarMensaje(mensajeEnviado, mensajeEnviado.roomId)
        }
    }

    private suspend fun cargarMensajesLocalmente(salaId: String) {
        try {
            chatLocal.obtenerMensajes(salaId).collect { mensajesList ->
                _mensajes.postValue(mensajesList.toMutableList())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _mensajes.postValue(mutableListOf())
        }
    }
}