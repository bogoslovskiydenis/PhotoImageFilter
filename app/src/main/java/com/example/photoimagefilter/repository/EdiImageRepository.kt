package com.example.photoimagefilter.repository

import android.graphics.Bitmap
import android.net.Uri

interface EdiImageRepository  {
    suspend fun prepareImagePreview (imageUri: Uri): Bitmap?
}