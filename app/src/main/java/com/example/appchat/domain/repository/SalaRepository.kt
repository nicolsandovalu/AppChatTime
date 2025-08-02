package com.example.appchat.domain.repository

import com.example.appchat.domain.model.Sala

interface SalaRepository {
    suspend fun obtenerSalas(): List<Sala>
}
