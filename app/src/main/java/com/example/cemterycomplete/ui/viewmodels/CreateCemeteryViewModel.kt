package com.example.cemterycomplete.ui.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cemterycomplete.data.CemeteryRepository
import com.example.cemterycomplete.data.entities.Cemetery
import kotlinx.coroutines.launch

class CreateCemeteryViewModel @ViewModelInject constructor(
    private var repository: CemeteryRepository,
    @Assisted private val savedStateHandle: SavedStateHandle

): ViewModel() {

    fun insertNewCemetery(cemetery: Cemetery){
        viewModelScope.launch {
            repository.insertCemetery(cemetery)
        }
    }

}