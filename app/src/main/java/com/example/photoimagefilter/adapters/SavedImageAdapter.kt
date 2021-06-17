package com.example.photoimagefilter.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoimagefilter.databinding.ItemContainerSavedImageBinding
import java.io.File

class SavedImageAdapter(private val savedImages: List<Pair<File, Bitmap>>) :
    RecyclerView.Adapter<SavedImageAdapter.SavedImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedImageViewHolder {
        val binding = ItemContainerSavedImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SavedImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedImageViewHolder, position: Int) {
        with(holder) {
            with(savedImages[position]) {
                binding.imageSaved.setImageBitmap(second)
            }
        }
    }

    override fun getItemCount() = savedImages.size


    inner class SavedImageViewHolder(val binding: ItemContainerSavedImageBinding) :
        RecyclerView.ViewHolder(binding.root)

}