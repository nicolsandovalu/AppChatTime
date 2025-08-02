package com.example.appchat.data.datasource.remote

import com.example.appchat.domain.model.Sala

interface SalaRemoteDataSource{
    suspend fun obtenerSalas(): List<Sala>
}