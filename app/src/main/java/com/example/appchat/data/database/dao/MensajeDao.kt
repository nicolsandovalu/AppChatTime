package com.example.appchat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appchat.data.database.entity.MensajeEntity
import com.example.appchat.domain.model.Mensaje
import kotlinx.coroutines.flow.Flow

@Dao
interface MensajeDao {

    //Inserta un mensaje en la base de datos o lo reemplaza si ya existe.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(mensajes: Mensaje)

    //Inserta una lista de mensajes en la base de datos o los reemplaza si ya existen.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMessages(mensajes: Mensaje)

    //Obtiene un flujo de mensajes para una sala específica, ordenados por marca de tiempo.
    @Query("SELECT * FROM mensajes WHERE roomId = :roomId ORDER BY timestamp ASC")
    fun getMessagesForRoom(roomId: String): Flow<List<Mensaje>>

    //Actualiza un mensaje existente en la base de datos
    @Update
    suspend fun updateMessage(mensajes: Mensaje)

    //Elimina todos los mensajes de una sala específica.
    @Query("DELETE FROM mensajes WHERE roomId = :roomId")
    suspend fun deleteMessagesForRoom(roomId: String)

    //Elimina todos los mensajes de la tabla
    @Query("DELETE FROM mensajes")
    suspend fun deleteAllMessages()

}