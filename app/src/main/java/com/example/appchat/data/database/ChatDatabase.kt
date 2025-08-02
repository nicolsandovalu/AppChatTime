package com.example.appchat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appchat.data.database.dao.MensajeDao
import com.example.appchat.data.database.entity.MensajeEntity

@Database(entities = [MensajeEntity::class], version = 1)
abstract class ChatDatabase : RoomDatabase {

    abstract fun mensajeDao(): MensajeDao
}