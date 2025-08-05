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

        val salaId = intent.getStringExtra("salaId") ?: "1"
        val salaNombre = intent.getStringExtra("salaNombre") ?: "Chat"

        title = salaNombre

        setupRecyclerView()
        observeViewModel()
        setupClickListeners(salaId)

        viewModel.conectarWebSocket(salaId)
    }

    private fun setupRecyclerView() {
        adapter = MensajeAdapter()
        binding.rvMensajes.layoutManager = LinearLayoutManager(this)
        binding.rvMensajes.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.mensajes.observe(this) { mensajes ->
            adapter.submitList(mensajes.toList())
            if (mensajes.isNotEmpty()) {
                binding.rvMensajes.scrollToPosition(mensajes.size -1)
            }
        }
    }

    private fun setupClickListeners(salaId: String) {
        binding.btnEnviar.setOnClickListener {
            val texto = binding.etMensaje.text.toString().trim()
            if (texto.isNotBlank()) {
                viewModel.enviarMensaje(texto)
                binding.etMensaje.text?.clear()
            }
        }
    }
}