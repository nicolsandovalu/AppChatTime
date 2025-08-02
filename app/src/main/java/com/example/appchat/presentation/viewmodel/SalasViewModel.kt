package com.example.appchat.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appchat.domain.model.Sala
import com.example.appchat.domain.usecase.ObtenerSalasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalasViewModel @Inject constructor(
    private val obtenerSalasUseCase: ObtenerSalasUseCase
) : ViewModel() {

    private val _salas = MutableLiveData<List<Sala>>()
    val salas: LiveData<List<Sala>> = _salas

    init {
        cargarSalas()
    }

    private fun cargarSalas() {
        viewModelScope.launch {
            _salas.value = obtenerSalasUseCase()
        }
    }
}
