package com.example.appchat.data.repository

import com.example.appchat.data.datasource.remote.SalaRemoteDataSource
import com.example.appchat.domain.model.Sala
import com.example.appchat.domain.repository.SalaRepository
import javax.inject.Inject

class SalaRepositoryImpl @Inject constructor(
    private val remote: SalaRemoteDataSource
) : SalaRepository {
    override suspend fun obtenerSalas(): List<Sala> {
        return remote.obtenerSalas()
    }
}
