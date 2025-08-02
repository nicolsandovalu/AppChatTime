package com.example.appchat.domain.usecase

import com.example.appchat.domain.repository.ChatRepository
import javax.inject.Inject

class EnviarMensajeUseCase @Inject constructor(
    private val repo: ChatRepository
) {
    fun enviar(mensaje: String) = repo.enviar(mensaje)
}
