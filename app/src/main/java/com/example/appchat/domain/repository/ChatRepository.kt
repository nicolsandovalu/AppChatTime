package com.example.appchat.domain.repository

interface ChatRepository {
    fun conectar(salaId: String, onMensaje: (String) -> Unit)
    fun enviar(mensaje: String)
    fun cerrar()
}
