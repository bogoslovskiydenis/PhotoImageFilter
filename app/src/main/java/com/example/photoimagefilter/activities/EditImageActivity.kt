package com.example.photoimagefilter.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.photoimagefilter.adapters.ImageFilterAdapter
import com.example.photoimagefilter.data.ImageFilter
import com.example.photoimagefilter.databinding.ActivityEditImageBinding
import com.example.photoimagefilter.listeners.ImageFilterListeners
import com.example.photoimagefilter.utilities.displayToast
import com.example.photoimagefilter.utilities.show
import com.example.photoimagefilter.viewmodel.EditImageViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.log

class EditImageActivity : AppCompatActivity(), ImageFilterListeners {

    companion object {
        const val KEY_FILTERED_IMAGE_URI = "filteredImageUrl"
    }

    private lateinit var binding: ActivityEditImageBinding

    private val viewModel: EditImageViewModel by viewModel()

    private lateinit var gpuImage: GPUImage

    // Image bitmaps
    private lateinit var originalBitmap: Bitmap
    private val filteredBitmap = MutableLiveData<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        // displayImagePreview()
        setupObservers()
        prepareImagePreview()
    }

    //region : Observers
    private fun setupObservers() {
        //imagePreviewUiState
        viewModel.imagePreviewUiState.observe(this, {
            val dataState = it ?: return@observe
            binding.previewProgressBar.visibility =
                if (dataState.isLoading) View.VISIBLE else View.GONE
            dataState.bitmap?.let { bitmap ->
                //for the first time filtered image = original image
                originalBitmap = bitmap
                filteredBitmap.value = bitmap
                with(originalBitmap) {
                    gpuImage.setImage(this)
                    binding.imagePreview.show()
                    viewModel.loadImageFilters(this)
                }

            } ?: run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })

        //imageFiltersUiState
        viewModel.imageFiltersUiState.observe(this, {
            val imageFiltersDataState = it ?: return@observe
            binding.imageFiltersProgressBar.visibility =
                if (imageFiltersDataState.isLoading) View.VISIBLE else View.GONE
            imageFiltersDataState.imageFilters?.let { imageFilters ->
                ImageFilterAdapter(imageFilters, this).also { adatpter ->
                    binding.filtersRecyclerView.adapter = adatpter
                }
            } ?: run {
                imageFiltersDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
        filteredBitmap.observe(this, { bitmap ->
            binding.imagePreview.setImageBitmap(bitmap)
        })

        viewModel.saveFilteredImageUiState.observe(this, {
            val saveFilteredImageDataState = it ?: return@observe
            if (saveFilteredImageDataState.isLoading) {
                binding.imageSave.visibility = View.GONE
                binding.savingProgressBar.visibility = View.VISIBLE
            } else {
                binding.savingProgressBar.visibility = View.VISIBLE
                binding.imageSave.visibility = View.GONE
            }
            saveFilteredImageDataState.uri?.let { savedImageUri ->
                Intent(
                    applicationContext,
                    FilteredImageActivity::class.java
                ).also { filteredImageIntent ->
                    filteredImageIntent.putExtra(KEY_FILTERED_IMAGE_URI, savedImageUri)
                    startActivity(filteredImageIntent)

                }
            } ?: run {
                saveFilteredImageDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }

    //endregion

    private fun prepareImagePreview() {

        gpuImage = GPUImage(applicationContext)
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
        binding.imageSave.setOnClickListener {
            filteredBitmap.value?.let { bitmap ->
                viewModel.saveFilteredImage(
                    bitmap
                )
            }
        }

        /*
        This will show image when we long click the ImageView untill we release click
         */
        binding.imagePreview.setOnLongClickListener {
            binding.imagePreview.setImageBitmap(originalBitmap)
            return@setOnLongClickListener false
        }
        binding.imagePreview.setOnClickListener {
            binding.imagePreview.setImageBitmap(filteredBitmap.value)
        }
    }

    override fun oneFilterSelected(imageFilter: ImageFilter) {
        with(imageFilter) {
            with(gpuImage) {
                setFilter(filter)
                filteredBitmap.value = bitmapWithFilterApplied
            }
        }
    }

}