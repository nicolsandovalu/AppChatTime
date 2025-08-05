package com.example.appchat.di

import android.content.Context
import androidx.room.Room
import com.example.appchat.data.database.AppDatabase
import com.example.appchat.data.database.dao.MensajeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "chat_db").build()

    @Provides
    fun provideMensajeDao(db: AppDatabase): MensajeDao = db.mensajeDao()
}
