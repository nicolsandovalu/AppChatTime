package com.example.appchat.di

import android.content.Context
import androidx.room.Room
import com.example.appchat.data.database.AppDatabase
import com.example.appchat.data.database.dao.MensajeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "chat_db"
        )
            .fallbackToDestructiveMigration() // Recomendado para desarrollo
            .build()
    }

    @Provides
    @Singleton
    fun provideMensajeDao(db: AppDatabase): MensajeDao {
        return db.mensajeDao()
    }
}
