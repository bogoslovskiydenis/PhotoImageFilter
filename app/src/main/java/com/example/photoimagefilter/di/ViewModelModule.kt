package com.example.photoimagefilter.di

import com.example.photoimagefilter.viewmodel.EditImageViewModel
import com.example.photoimagefilter.viewmodel.SavedImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EditImageViewModel(editImageRepository = get()) }
    viewModel { SavedImagesViewModel(savedImagesRepository = get()) }
}