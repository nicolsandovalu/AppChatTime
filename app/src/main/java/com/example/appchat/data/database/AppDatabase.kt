package com.example.appchat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.appchat.data.database.converters.Converters
import com.example.appchat.data.database.dao.MensajeDao
import com.example.appchat.data.database.dao.SalaDao
import com.example.appchat.data.database.dao.UserDao
import com.example.appchat.domain.model.Mensaje
import com.example.appchat.domain.model.Sala
import com.example.appchat.domain.model.User

@Database(

    entities = [User::class, Sala::class, Mensaje::class],
    version = 2,
    exportSchema = true
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun salaDao(): SalaDao

    abstract fun mensajeDao(): MensajeDao
}