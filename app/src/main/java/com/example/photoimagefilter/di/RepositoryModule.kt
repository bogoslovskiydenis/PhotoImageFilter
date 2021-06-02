package com.example.photoimagefilter.di

import com.example.photoimagefilter.repository.EdiImageRepository
import com.example.photoimagefilter.repository.EditImageRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<EdiImageRepository> {  EditImageRepositoryImpl(androidContext()) }
}