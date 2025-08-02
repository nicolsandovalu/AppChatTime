package com.example.appchat.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
}

