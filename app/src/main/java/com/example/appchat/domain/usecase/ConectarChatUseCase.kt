package com.example.appchat.domain.usecase

import com.example.appchat.domain.repository.ChatRepository
import javax.inject.Inject

class ConectarChatUseCase @Inject constructor(
    private val repo: ChatRepository
) {
    fun conectar(salaId: String, onMensaje: (String) -> Unit) {
        repo.conectar(salaId, onMensaje)
    }
}
