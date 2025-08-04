package com.example.appchat.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")

data class User(
    @PrimaryKey val id: String, //id único del usuario
    val username: String, //nombre de usuario
    val email: String, //email de usuario
    val avatarUrl: String? = null //url del avatar opcional
) {

    //Construtor sin argumentos requerido por Firebase Firestore para deserializar
    constructor(): this("", "", "", null)
}


