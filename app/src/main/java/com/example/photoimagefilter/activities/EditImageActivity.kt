package com.example.photoimagefilter.activities

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.photoimagefilter.databinding.ActivityEditImageBinding

class EditImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        displayImagePreview()

    }

    private fun displayImagePreview() {
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URL)?.let { imageUrl->
            val inputStream = contentResolver.openInputStream(imageUrl)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.imagePreview.setImageBitmap(bitmap)
            binding.imagePreview.visibility = View.VISIBLE
        }
    }

    private fun setListeners() {
        binding.imageback.setOnClickListener {
            onBackPressed()
        }
    }

}