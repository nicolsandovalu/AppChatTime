package com.example.appchat.presentation.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appchat.databinding.ActivityChatBinding
import com.example.appchat.presentation.adapter.MensajeAdapter
import com.example.appchat.presentation.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: MensajeAdapter
    private var currentSalaId: String? = null // Variable para almacenar el salaId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("salaNombre")

        // Obtener salaId del Intent
        currentSalaId = intent.getStringExtra("salaId")
        val salaId = currentSalaId ?: return // Si no hay salaId, sal de la actividad

        // Configurar RecyclerView y el adaptador
        adapter = MensajeAdapter()
        binding.rvMensajes.layoutManager = LinearLayoutManager(this)
        binding.rvMensajes.adapter = adapter

        // Conectar ViewModel y Observer
        viewModel.conectarWebSocket(salaId) // Pasa el salaId al ViewModel

        viewModel.mensajes.observe(this) { mensajes ->
            adapter.submitList(mensajes)
            if (mensajes.isNotEmpty()) {
                // FIX: Cambia 'messages' a 'mensajes'
                binding.rvMensajes.scrollToPosition(mensajes.size - 1)
            }
        }

        // Configurar el botón de enviar
        binding.btnEnviar.setOnClickListener {
            val mensajeTexto = binding.etMensaje.text.toString().trim()
            if (mensajeTexto.isNotEmpty()) {
                viewModel.enviarMensaje(mensajeTexto) // El ViewModel ahora toma el salaId internamente
                binding.etMensaje.text.clear()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}