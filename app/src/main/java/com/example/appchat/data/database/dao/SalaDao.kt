package com.example.appchat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appchat.domain.model.Sala
import kotlinx.coroutines.flow.Flow

@Dao
interface SalaDao {

    //Inserta una lista de salas de chat en la base de datos o las reemplaza si ya existen.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllChatRooms(salas: List<Sala>)


     //Obtiene un flujo de todas las salas de chat ordenadas por nombre.
    @Query("SELECT * FROM sala ORDER BY name ASC")
     fun getAllChatRooms(): Flow<List<Sala>>

     @Query("SELECT * FROM sala WHERE id = :roomId")
     suspend fun getChatRoomById(roomId: String): Sala?

     //Elimina todas las salas del chat
     @Query("DELETE FROM sala")
     suspend fun deleteAllChatRooms()

}
