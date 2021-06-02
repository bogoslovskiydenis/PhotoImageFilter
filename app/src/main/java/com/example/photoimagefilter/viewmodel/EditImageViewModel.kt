package com.example.photoimagefilter.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoimagefilter.repository.EdiImageRepository
import com.example.photoimagefilter.utilities.Coroutines

class EditImageViewModel(private val ediImageRepository: EdiImageRepository) : ViewModel() {

    private val imagePreviewDataState = MutableLiveData<ImagePreviewDataState>()
    val emitUiState: LiveData<ImagePreviewDataState> get() = imagePreviewDataState

    fun prepareImagePreview(imageUri: Uri){
        Coroutines.io {
            kotlin.runCatching {
                emitUiState(isLoading = false)
                ediImageRepository.prepareImagePreview(imageUri)
            }.onSuccess {
                bitbap->
                if(bitbap!=null){
                    emitUiState(bitmap = bitbap)
                }else{
                    emitUiState(error = "Unable to prepare image preview")
                }
            }.onFailure {
                emitUiState(error = it.message.toString())
            }
        }
    }


    private fun emitUiState(
        isLoading: Boolean = false,
        bitmap: Bitmap? = null,
        error: String? = null
    ){
        val dataState = ImagePreviewDataState(isLoading, bitmap, error)
        imagePreviewDataState.value = dataState
    }


    data class ImagePreviewDataState(
        val isLoading: Boolean,
        val bitmap: Bitmap?,
        val error: String?
    )
}