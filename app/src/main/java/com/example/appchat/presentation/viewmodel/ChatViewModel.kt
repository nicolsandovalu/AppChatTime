package com.example.appchat.presentation.viewmodel

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

            conectar.conectar(salaId) { nuevoMensaje ->
                viewModelScope.launch {
                    _mensajes.value?.add(nuevoMensaje)
                    _mensajes.postValue(_mensajes.value)

                    chatLocal.guardarMensaje(
                        Mensaje(
                            contenido = nuevoMensaje,
                            remitente = "otro",
                            timestamp = System.currentTimeMillis()
                        ),
                        salaId
                    )
                }
            }
        }
    }

    fun enviarMensaje(texto: String) {
        enviar.enviar(texto)
        val mensaje = "Yo: $texto"
        _mensajes.value?.add(mensaje)
        _mensajes.postValue(_mensajes.value)
    }

    private suspend fun cargarMensajesLocalmente(salaId: String) {
        try {
            val mensajes = chatLocal.obtenerMensajes(salaId)
            _mensajes.postValue(mensajes.map {
                "${it.remitente}: ${it.contenido} (${it.estado.name.lowercase()})"
            }.toMutableList())
        } catch (e: Exception) {
            _mensajes.postValue(mutableListOf())
        }
    }
}