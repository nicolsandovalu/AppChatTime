package com.example.appchat.services

import android.app.Service
import android.app.Service.START_STICKY
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SyncService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Lógica de sincronización en background
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}