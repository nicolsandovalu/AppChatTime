package com.example.appchat.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        println("Mensaje recibir de: ${message.from}")
        message.notification?.let {
            println("Notificación Título del mensaje: ${it.title}")
            println("Notificación Cuerpo del mensaje: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        println("Token actualizado: $token")
    }
}