package com.example.cemterycomplete.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cemterycomplete.data.CemeteryRepository
import com.example.cemterycomplete.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
    refresh cemetery list -> sets an error message if failure

    get all cemeteries from the database which sets a live data member
 */

class CemeteryListViewModel @ViewModelInject constructor(
    private val repository: CemeteryRepository
) : ViewModel() {



    private val _cemeteryResponse = MutableLiveData<Resource<String>>()
    val cemeteryResponse: LiveData<Resource<String>> = _cemeteryResponse

    init {
        refreshCemeteryList()
    }

    val cemeteryList = repository.getAllCemeteries()


    fun refreshCemeteryList() {
        viewModelScope.launch {

            _cemeteryResponse.value = repository.refreshCemeteryList()
        }
    }
}