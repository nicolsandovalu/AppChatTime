package com.example.appchat.presentation.ui.chat

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
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
    private var currentSalaId: String? = null

    // Launcher para seleccionar una imagen de la galería
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Cuando se selecciona una imagen, llama al ViewModel para enviarla
            currentSalaId?.let { salaId ->
                viewModel.enviarImagen(it, salaId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("salaNombre")

        currentSalaId = intent.getStringExtra("salaId")
        val salaId = currentSalaId ?: return

        adapter = MensajeAdapter()
        binding.rvMensajes.layoutManager = LinearLayoutManager(this)
        binding.rvMensajes.adapter = adapter

        viewModel.conectarWebSocket(salaId)

        viewModel.mensajes.observe(this) { mensajes ->
            adapter.submitList(mensajes)
            if (mensajes.isNotEmpty()) {
                binding.rvMensajes.scrollToPosition(mensajes.size - 1)
            }
        }

        binding.btnEnviar.setOnClickListener {
            val mensajeTexto = binding.etMensaje.text.toString().trim()
            if (mensajeTexto.isNotEmpty()) {
                viewModel.enviarMensaje(mensajeTexto)
                binding.etMensaje.text.clear()
            }
        }

        // Listener para el nuevo botón de adjuntar imagen
        binding.btnAdjuntar.setOnClickListener {
            pickImageLauncher.launch("image/*") // Abre el selector de imágenes
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}