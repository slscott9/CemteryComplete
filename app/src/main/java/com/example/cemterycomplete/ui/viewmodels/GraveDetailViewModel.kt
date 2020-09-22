package com.example.cemterycomplete.ui.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.cemterycomplete.data.CemeteryRepository

class GraveDetailViewModel @ViewModelInject constructor(
    private val repository: CemeteryRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(){


    companion object {
        const val GRAVE_SELECTED = "grave_selected"
    }
}