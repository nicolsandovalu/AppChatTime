package com.example.appchat.presentation.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appchat.databinding.ActivityChatBinding
import com.example.appchat.presentation.adapter.MensajeAdapter
import com.example.appchat.presentation.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: MensajeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("salaNombre")

        adapter = MensajeAdapter()
        binding.rvMensajes.layoutManager = LinearLayoutManager(this)
        binding.rvMensajes.adapter = adapter

        val salaId = intent.getStringExtra("salaId") ?: return
        viewModel.conectarWebSocket(salaId)

        viewModel.mensajes.observe(this) { mensajes ->
            adapter.submitList(mensajes)
            binding.rvMensajes.scrollToPosition(mensajes.size - 1)
        }

        binding.btnEnviar.setOnClickListener {
            val mensaje = binding.etMensaje.text.toString()
            if (mensaje.isNotEmpty()) {
                viewModel.enviarMensaje(mensaje)
                binding.etMensaje.text.clear()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}