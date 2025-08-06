package com.example.appchat.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appchat.domain.model.Mensaje
import com.example.appchat.domain.repository.ChatLocalRepository
import com.example.appchat.domain.usecase.ConectarChatUseCase
import com.example.appchat.domain.usecase.EnviarMensajeUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val conectar: ConectarChatUseCase,
    private val enviar: EnviarMensajeUseCase,
    private val chatLocal: ChatLocalRepository,
    private val gson: Gson
) : ViewModel() {

    private val _mensajes = MutableLiveData<MutableList<Mensaje>>(mutableListOf())
    val mensajes: LiveData<MutableList<Mensaje>> = _mensajes

    private var currentRoomId: String? = null

    fun conectarWebSocket(salaId: String) {
        currentRoomId = salaId
        viewModelScope.launch {
            cargarMensajesLocalmente(salaId)

            conectar.conectar(salaId) { mensajeJson ->
                viewModelScope.launch {
                    try {
                        val nuevoMensaje = gson.fromJson(mensajeJson, Mensaje::class.java)

                        if (nuevoMensaje.roomId == currentRoomId) {
                            _mensajes.value?.add(nuevoMensaje)
                            _mensajes.postValue(_mensajes.value)
                        }
                        chatLocal.guardarMensaje(nuevoMensaje, salaId)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        println("Error al deserializar mensaje JSON: $mensajeJson")
                    }
                }
            }
        }
    }

    fun enviarMensaje(texto: String) {
        val salaId = currentRoomId ?: return

        val mensajeEnviado = Mensaje(
            id = UUID.randomUUID().toString(),
            content = texto,
            senderId = "Yo",
            timestamp = System.currentTimeMillis(),
            roomId = salaId,
            type = Mensaje.MessageType.TEXT,
            status = Mensaje.MessageStatus.SENT
        )

        _mensajes.value?.add(mensajeEnviado)
        _mensajes.postValue(_mensajes.value)

        val mensajeJson = gson.toJson(mensajeEnviado)
        enviar.enviar(mensajeJson)

        viewModelScope.launch {
            chatLocal.guardarMensaje(mensajeEnviado, mensajeEnviado.roomId)
        }
    }

    // Nueva función para enviar imágenes
    fun enviarImagen(imageUri: Uri, salaId: String) {
        val mensajeImagen = Mensaje(
            id = UUID.randomUUID().toString(),
            content = "Imagen adjunta", // Texto descriptivo
            senderId = "Yo",
            timestamp = System.currentTimeMillis(),
            roomId = salaId,
            type = Mensaje.MessageType.IMAGE, // Tipo de mensaje IMAGE
            fileUrl = imageUri.toString(), // Guarda la URI local de la imagen como String
            status = Mensaje.MessageStatus.SENT
        )

        _mensajes.value?.add(mensajeImagen)
        _mensajes.postValue(_mensajes.value)

        val mensajeJson = gson.toJson(mensajeImagen)
        enviar.enviar(mensajeJson) // Envía el JSON completo (incluye fileUrl)

        viewModelScope.launch {
            chatLocal.guardarMensaje(mensajeImagen, mensajeImagen.roomId)
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