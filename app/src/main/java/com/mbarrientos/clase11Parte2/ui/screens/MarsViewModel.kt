package com.mbarrientos.clase11Parte2.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbarrientos.clase11Parte2.network.MarsApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {

    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val photos = MarsApi.retrofitService.getPhotos()
                marsUiState = MarsUiState.Success("Success: ${photos.size} Mars photos retrieved")
            } catch (e: IOException) {
                marsUiState = MarsUiState.Error
            }
        }
    }
}
