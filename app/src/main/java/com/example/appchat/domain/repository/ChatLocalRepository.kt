package com.example.appchat.domain.repository

import com.example.appchat.domain.model.Mensaje

interface ChatLocalRepository {
    val dao: Any

    suspend fun obtenerMensajes(salaId: String): List<Mensaje>
    suspend fun guardarMensaje(mensaje: Mensaje, salaId: String)

    override suspend fun actualizarEstado(timestamp: Long, estado: EstadoMensaje) {
        dao.actualizarEstadoMensaje(timestamp, estado.name)
    }

}