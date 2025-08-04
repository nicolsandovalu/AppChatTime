package com.example.appchat.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity para la persistencia local
@Entity(tableName = "sala")

data class Sala(
    @PrimaryKey
    val id: String,
    val name: String, //elementos sala de chat
    val description: String? = null,
    val participants: List<String> = emptyList(), //list id de participantes
    val createdAt: Long = System.currentTimeMillis() //marca de tiempo de creación de la sala

){
    //constructor sin argumentos requerido por Firebase Firestore para deserialización
    constructor() : this("", "", null, emptyList(), 0L)
}