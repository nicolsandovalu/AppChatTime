package com.example.appchat.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appchat.databinding.ItemMensajeBinding
import com.example.appchat.domain.model.Mensaje

class MensajeAdapter : RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

    // Cambia el tipo de la lista para manejar objetos Mensaje
    private val mensajes = mutableListOf<Mensaje>()

    // El metodo `submitList` ahora acepta una lista de Mensaje
    fun submitList(nuevos: List<Mensaje>) {
        mensajes.clear()
        mensajes.addAll(nuevos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val binding = ItemMensajeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MensajeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        holder.bind(mensajes[position])
    }

    override fun getItemCount(): Int = mensajes.size

    inner class MensajeViewHolder(val binding: ItemMensajeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mensaje: Mensaje) {
            binding.tvMensaje.text = "${mensaje.senderId}: ${mensaje.content}"
        }
    }
}