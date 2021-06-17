package com.example.photoimagefilter.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.photoimagefilter.databinding.ActivitySaveImageBinding
import com.example.photoimagefilter.utilities.displayToast
import com.example.photoimagefilter.viewmodel.SavedImagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaveImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveImageBinding
    private val viewModel: SavedImagesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
        setListeners()
        viewModel.loadSavedImages()
    }

    private fun setupObserver() {
        viewModel.savedImagesUiState.observe(this, {
            val savedImagesDataState = it ?: return@observe
            binding.savedImagesProgressBar.visibility
            if (savedImagesDataState.isLoading) View.VISIBLE else View.GONE
            savedImagesDataState.savedImages?.let { savedImage ->
                displayToast("${savedImage.size} image loaded")
            } ?: kotlin.run {
                savedImagesDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }

    private fun setListeners() {
        binding.imageback.setOnClickListener { onBackPressed() }
    }
}