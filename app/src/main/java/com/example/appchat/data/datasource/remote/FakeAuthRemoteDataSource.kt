package com.example.appchat.data.datasource.remote

import javax.inject.Inject

class FakeAuthRemoteDataSource @Inject constructor(): AuthRemoteDataSource {
    override suspend fun login(email: String, password: String): Boolean {
        return email == "admin@chat.com" && password == "1234"
    }
}