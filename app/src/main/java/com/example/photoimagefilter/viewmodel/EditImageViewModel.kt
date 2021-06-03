package com.example.photoimagefilter.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoimagefilter.repository.EdiImageRepository
import com.example.photoimagefilter.utilities.Coroutines

class EditImageViewModel(private val editImageRepository: EdiImageRepository) : ViewModel() {

    val imagePreviewDataState = MutableLiveData<ImagePreviewDataState>()
    val emitUiState: LiveData<ImagePreviewDataState> get() = imagePreviewDataState

    fun prepareImagePreview(imageUri: Uri){
        Coroutines.io {
            kotlin.runCatching {
                emitImagePreviewUiState(isLoading = false)
                editImageRepository.prepareImagePreview(imageUri)
            }.onSuccess {
                bitmap->
                if(bitmap!=null){
                    emitImagePreviewUiState(bitmap = bitmap)
                }else{
                    emitImagePreviewUiState(error = "Unable to prepare image preview")
                }
            }.onFailure {
                emitImagePreviewUiState(error = it.message.toString())
            }
        }
    }


    private fun emitImagePreviewUiState(
        isLoading: Boolean = false,
        bitmap: Bitmap? = null,
        error: String? = null
    ){
        val dataState = ImagePreviewDataState(isLoading, bitmap, error)
        imagePreviewDataState.postValue(dataState)
    }


    data class ImagePreviewDataState(
        val isLoading: Boolean,
        val bitmap: Bitmap?,
        val error: String?
    )
}