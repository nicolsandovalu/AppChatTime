package com.example.appchat.di


import com.example.appchat.domain.repository.AuthRepository
import com.example.appchat.domain.usecase.LoginUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // O ViewModelComponent si prefieres
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUserUseCase(authRepository: AuthRepository): LoginUserUseCase {
        return LoginUserUseCase(authRepository)
    }

}