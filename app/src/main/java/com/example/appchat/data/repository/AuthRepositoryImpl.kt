package com.example.appchat.data.repository

import com.example.appchat.data.datasource.remote.AuthRemoteDataSource
import com.example.appchat.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        return remote.login(email, password)
    }
}