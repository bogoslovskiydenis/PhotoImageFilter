package com.example.photoimagefilter.repository

import android.graphics.Bitmap
import java.io.File

interface SaveImagesRepository {
    suspend fun loadSavedImages(): List<Pair<File, Bitmap>>?
}