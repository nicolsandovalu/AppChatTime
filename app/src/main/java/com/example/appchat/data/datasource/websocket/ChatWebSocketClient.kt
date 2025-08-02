package com.example.appchat.data.datasource.websocket

import com.bumptech.glide.request.Request
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class ChatWebSocketClient @Inject constructor() {

    private var webSocket: WebSocket = null
    private var listener: ChatWebSocketListener? = null

    fun connect(url: String, listener: ChatWebSocketListener) {
        this.listener = listener
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, listener)
    }

    fun sendMenssage(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "Cliente salió")
    }

    abstract class ChatWebSocketListener: WebSocketListener(){

        abstract fun onNuevoMensaje(mensaje: String)
        override fun onMessage(webSocket: WebSocket, text: String) {
            onNuevoMensaje(text)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            t.printStackTrace()
        }
    }
}