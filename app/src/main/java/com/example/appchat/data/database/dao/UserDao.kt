package com.example.appchat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appchat.domain.model.User

@Dao
interface UserDao{
    //Inserta un usuario en la base de datos o lo reemplaza si ya existe.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    /**
     * Obtiene un usuario por su ID.
     * @param userId El ID del usuario.
     * @return El usuario si se encuentra, null de lo contrario.
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): User?

    /**
     * Elimina todos los usuarios de la tabla.
     */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getCurrentUser(): User?
}
