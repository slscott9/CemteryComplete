package com.example.cemterycomplete.ui.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.cemterycomplete.data.CemeteryRepository
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.data.entities.Grave

class CemeteryDetailViewModel @ViewModelInject constructor(
    private val repository: CemeteryRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _cemeteryId = MutableLiveData<Int>(savedStateHandle.get(CEMETERY_SELECTED))

    val cemeterySelected = Transformations.switchMap(_cemeteryId) {
        repository.getCemeteryWithRowId(it)
    }

    val graveList: LiveData<List<Grave>> = Transformations.switchMap(cemeterySelected){
        repository.getGravesWithCemeteryId(it.cemeteryRowId)
    }


    companion object {
        const val CEMETERY_SELECTED = "cem_selected"
    }
}