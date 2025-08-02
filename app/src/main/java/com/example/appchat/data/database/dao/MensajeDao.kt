package com.example.appchat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appchat.data.database.entity.MensajeEntity

@Dao
interface MensajeDao {

    @Query("SELECT * FROM mensaje WHERE salaId ORDER BY timestamp ASC")
    suspend fun getMensajesPorSala(salaId: String): List<MensajeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertarMensaje(mensaje: MensajeEntity)

    @Query("DELETE FROM mensajes WHERE salaId = :salaId")
    suspend fun borrarMensajesDeSala(salaId: String)

    @Query("UPDATE mensajes SET estado = :nuevoEstado WHERE timestamp = :timestamp")
    suspend fun actualizarEstadoMensaje(timestamp: Long, nuevoEstado: String)
}