package com.example.photoimagefilter.di

import com.example.photoimagefilter.repository.EdiImageRepository
import com.example.photoimagefilter.repository.EditImageRepositoryImpl
import com.example.photoimagefilter.repository.SaveImagesRepository
import com.example.photoimagefilter.repository.SavedImagesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<EdiImageRepository> {  EditImageRepositoryImpl(androidContext()) }

    factory<SaveImagesRepository> { SavedImagesRepositoryImpl(androidContext()) }
}