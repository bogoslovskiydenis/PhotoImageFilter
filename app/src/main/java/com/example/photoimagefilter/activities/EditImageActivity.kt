package com.example.photoimagefilter.activities

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.photoimagefilter.databinding.ActivityEditImageBinding
import com.example.photoimagefilter.utilities.displayToast
import com.example.photoimagefilter.utilities.show
import com.example.photoimagefilter.viewmodel.EditImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditImageBinding

    private val viewModel: EditImageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
       // displayImagePreview()
        setupObservers()
        prepareImagePreview()
    }


    private fun setupObservers() {
        viewModel.imagePreviewDataState.observe(this, {
            val dataState = it ?: return@observe
            binding.previewProgressBar.visibility =
                if (dataState.isLoading) View.VISIBLE else View.GONE
            dataState.bitmap?.let { bitmap ->
                binding.imagePreview.setImageBitmap(bitmap)
                binding.imagePreview.show()
            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }

    private fun prepareImagePreview() {
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URL)?.let { imageUrl ->

            viewModel.prepareImagePreview(imageUrl)
        }
    }


    private fun displayImagePreview() {
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URL)?.let { imageUrl ->
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