package com.example.appchat.di

import android.content.Context
import com.example.appchat.data.database.dao.MensajeDao
import com.example.appchat.data.datasource.remote.AuthRemoteDataSource
import com.example.appchat.data.datasource.remote.FakeAuthRemoteDataSource
import com.example.appchat.data.datasource.remote.FakeSalaRemoteDataSource
import com.example.appchat.data.datasource.remote.SalaRemoteDataSource
import com.example.appchat.data.datasource.websocket.ChatWebSocketClient
import com.example.appchat.data.repository.AuthRepositoryImpl
import com.example.appchat.data.repository.ChatRepositoryImpl
import com.example.appchat.data.repository.SalaRepositoryImpl
import com.example.appchat.domain.repository.AuthRepository
import com.example.appchat.domain.repository.ChatLocalRepository
import com.example.appchat.domain.repository.ChatLocalRepositoryImpl
import com.example.appchat.domain.repository.ChatRepository
import com.example.appchat.domain.repository.SalaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Proporciona el contexto de la aplicación
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    // Fuentes de datos remotas
    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(): AuthRemoteDataSource {
        return FakeAuthRemoteDataSource()
    }

    @Provides
    @Singleton
    fun provideSalaRemoteDataSource(): SalaRemoteDataSource {
        return FakeSalaRemoteDataSource()
    }

    // Repositorios
    @Provides
    @Singleton
    fun provideAuthRepository(
        remote: AuthRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(remote)
    }

    @Provides
    @Singleton
    fun provideSalaRepository(
        remote: SalaRemoteDataSource
    ): SalaRepository {
        return SalaRepositoryImpl(remote)
    }

    // WebSocket y Chat
    @Provides
    @Singleton
    fun provideChatWebSocketClient(): ChatWebSocketClient {
        return ChatWebSocketClient()
    }

    @Provides
    @Singleton
    fun provideChatRepository(client: ChatWebSocketClient): ChatRepository {
        return ChatRepositoryImpl(client)
    }

    @Provides
    @Singleton
    fun provideChatLocalRepository(dao: MensajeDao): ChatLocalRepository {
        return ChatLocalRepositoryImpl(dao)
    }
}