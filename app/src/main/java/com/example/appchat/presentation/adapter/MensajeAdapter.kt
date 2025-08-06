package com.example.appchat.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appchat.databinding.ItemMensajeBinding
import com.example.appchat.domain.model.Mensaje

class MensajeAdapter : RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

    private val mensajes = mutableListOf<Mensaje>()

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
            binding.tvRemitente.text = mensaje.senderId

            // Lógica para mostrar texto o imagen
            if (mensaje.type == Mensaje.MessageType.TEXT) {
                binding.tvMensaje.text = mensaje.content
                binding.tvMensaje.visibility = View.VISIBLE
                binding.ivImagenMensaje.visibility = View.GONE // Oculta la imagen
            } else if (mensaje.type == Mensaje.MessageType.IMAGE) {
                binding.tvMensaje.visibility = View.GONE // Oculta el texto
                binding.ivImagenMensaje.visibility = View.VISIBLE // Muestra la imagen

                // Carga la imagen usando Glide desde la URI local
                mensaje.fileUrl?.let { uriString ->
                    Glide.with(binding.ivImagenMensaje.context)
                        .load(uriString) // Carga la URI (que es un String)
                        .placeholder(android.R.drawable.ic_menu_gallery) // Placeholder mientras carga
                        .error(android.R.drawable.ic_menu_report_image) // Imagen de error si falla
                        .into(binding.ivImagenMensaje)
                } ?: run {
                    // Si fileUrl es nulo, oculta la imagen y muestra un mensaje de error o fallback
                    binding.ivImagenMensaje.visibility = View.GONE
                    binding.tvMensaje.visibility = View.VISIBLE
                    binding.tvMensaje.text = "Error: Imagen no disponible"
                }
            } else {
                // Para otros tipos de mensaje (como FILE, si los implementas)
                binding.tvMensaje.text = "Tipo de mensaje no soportado"
                binding.tvMensaje.visibility = View.VISIBLE
                binding.ivImagenMensaje.visibility = View.GONE
            }
        }
    }
}