package com.example.appchat.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appchat.databinding.ItemSalaBinding
import com.example.appchat.domain.model.Sala

class SalaAdapter(
    private val onSalaClick: (Sala) -> Unit
) : RecyclerView.Adapter<SalaAdapter.SalaViewHolder>() {

    private val listaSalas = mutableListOf<Sala>()

    fun submitList(nuevasSalas: List<Sala>) {
        listaSalas.clear()
        listaSalas.addAll(nuevasSalas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalaViewHolder {
        val binding = ItemSalaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SalaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SalaViewHolder, position: Int) {
        holder.bind(listaSalas[position])
    }

    override fun getItemCount(): Int = listaSalas.size

    inner class SalaViewHolder(private val binding: ItemSalaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sala: Sala) {
            binding.tvNombreSala.text = sala.nombre
            binding.tvDescripcionSala.text = sala.descripcion
            binding.root.setOnClickListener { onSalaClick(sala) }
        }
    }
}
