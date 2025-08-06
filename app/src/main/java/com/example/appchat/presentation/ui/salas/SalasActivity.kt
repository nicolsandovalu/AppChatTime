package com.example.appchat.presentation.ui.salas

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appchat.databinding.ActivitySalasBinding
import com.example.appchat.presentation.adapter.SalaAdapter
import com.example.appchat.presentation.ui.chat.ChatActivity
import com.example.appchat.presentation.viewmodel.SalasViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class SalasActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalasBinding
    private val viewModel: SalasViewModel by viewModels()
    private lateinit var adapter: SalaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Salas de Chat"

        adapter = SalaAdapter { sala ->
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("salaId", sala.id)
            intent.putExtra("salaNombre", sala.name)
            startActivity(intent)
        }

        binding.rvSalas.layoutManager = LinearLayoutManager(this)
        binding.rvSalas.adapter = adapter

        viewModel.salas.observe(this) { salas ->
            adapter.submitList(salas)
        }
    }
}