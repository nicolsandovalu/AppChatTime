package com.example.appchat.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appchat.domain.model.Mensaje
import com.example.appchat.domain.repository.ChatLocalRepository
import com.example.appchat.domain.usecase.ConectarChatUseCase
import com.example.appchat.domain.usecase.EnviarMensajeUseCase
import com.google.gson.Gson // Asegúrate de que Gson esté importado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val conectar: ConectarChatUseCase,
    private val enviar: EnviarMensajeUseCase,
    private val chatLocal: ChatLocalRepository,
    private val gson: Gson // Inyecta Gson aquí
) : ViewModel() {

    private val _mensajes = MutableLiveData<MutableList<Mensaje>>(mutableListOf())
    val mensajes: LiveData<MutableList<Mensaje>> = _mensajes

    // Variable para almacenar el ID de la sala actual
    private var currentRoomId: String? = null

    fun conectarWebSocket(salaId: String) {
        currentRoomId = salaId // Guarda el ID de la sala actual
        viewModelScope.launch {
            cargarMensajesLocalmente(salaId)

            conectar.conectar(salaId) { mensajeJson -> // Este 'mensajeJson' es un String del WebSocket
                viewModelScope.launch {
                    try {
                        // Deserializa el JSON a un objeto Mensaje
                        val nuevoMensaje = gson.fromJson(mensajeJson, Mensaje::class.java)

                        // Solo añade el mensaje si corresponde a la sala actual
                        if (nuevoMensaje.roomId == currentRoomId) {
                            _mensajes.value?.add(nuevoMensaje)
                            _mensajes.postValue(_mensajes.value) // Notifica a los observadores
                        }
                        chatLocal.guardarMensaje(nuevoMensaje, salaId) // Guarda el mensaje localmente
                    } catch (e: Exception) {
                        // Maneja errores si el JSON no es válido o no se puede deserializar
                        e.printStackTrace()
                        println("Error al deserializar mensaje JSON: $mensajeJson")
                    }
                }
            }
        }
    }

    fun enviarMensaje(texto: String) {
        // Asegúrate de usar el ID de la sala actual
        val salaId = currentRoomId ?: return // Si no hay sala, no envía el mensaje

        val mensajeEnviado = Mensaje(
            content = texto,
            senderId = "Yo", // Puedes obtener el ID del usuario logueado aquí
            timestamp = System.currentTimeMillis(),
            roomId = salaId, // Usa el ID de la sala actual
            type = Mensaje.MessageType.TEXT,
            status = Mensaje.MessageStatus.SENT
        )

        // Agrega el mensaje a la lista local *antes* de enviarlo para mostrarlo inmediatamente
        _mensajes.value?.add(mensajeEnviado)
        _mensajes.postValue(_mensajes.value) // Notifica a los observadores

        // Serializa el objeto Mensaje a JSON antes de enviarlo por el WebSocket
        val mensajeJson = gson.toJson(mensajeEnviado)
        enviar.enviar(mensajeJson) // Envía el JSON completo

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