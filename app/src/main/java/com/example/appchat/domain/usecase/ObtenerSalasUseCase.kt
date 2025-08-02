package com.example.appchat.domain.usecase

import com.example.appchat.domain.model.Sala
import com.example.appchat.domain.repository.SalaRepository
import javax.inject.Inject

class ObtenerSalasUseCase @Inject constructor(
    private val repository: SalaRepository
) {
    suspend operator fun invoke(): List<Sala> = repository.obtenerSalas()
}
