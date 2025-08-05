package com.example.appchat.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.remoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        println("Mensaje recibir de: ${remoteMessage.from}")
        remoteMessage.notification?.let {
            println("Notificación Título del mensaje: ${it.title}")
            println("Notificación Cuerpo del mensaje: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        println("Token actualizado: $token")
    }
}