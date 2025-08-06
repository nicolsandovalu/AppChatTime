package com.example.appchat.utils

object Constants {

    // URL de ejemplo para el servidor WebSocket.
    // **IMPORTANTE**: Sustituir con la URL real de tu servidor WebSocket.
    // Ejemplo: const val WEBSOCKET_URL = "ws://192.168.1.100:8080/chat"
    const val WEBSOCKET_URL = "ws://your_websocket_server_ip:8080/chat" // Reemplaza esto con tu URL.

    //claves para sharedPreferences o argumentos de intent
    const val EXTRA_ROOM_ID = "extra_room_id"
    const val EXTRA_ROOM_NAME = "extra_room_name"

    //Nombres de colecciones de Firestore
    const val FIRESTORE_COLLECTION_USERS = "users"
    const val FIRESTORE_COLLECTION_SALA = "sala"
    const val FIRESTORE_COLLECTION_MENSAJE = "mensajes"

    // Directorios de almacenamiento de Firebase Storage
    const val FIREBASE_STORAGE_IMAGES_PATH = "chat_images/"
    const val FIREBASE_STORAGE_FILES_PATH = "chat_files/"

    // Otros
    const val ENCRYPTION_ALGORITHM = "AES"
    const val ENCRYPTION_TRANSFORMATION = "AES/CBC/PKCS5Padding"
    const val ENCRYPTION_KEY_ALIAS = "chat_app_encryption_key"
}