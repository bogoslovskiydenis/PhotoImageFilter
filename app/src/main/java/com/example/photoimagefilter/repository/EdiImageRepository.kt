package com.example.photoimagefilter.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.photoimagefilter.data.ImageFilter

interface EdiImageRepository  {
    suspend fun prepareImagePreview (imageUri: Uri): Bitmap?
    suspend fun getImageFilters(image: Bitmap): List<ImageFilter>
}