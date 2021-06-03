package com.example.photoimagefilter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoimagefilter.data.ImageFilter
import com.example.photoimagefilter.databinding.ItemContaiterFilterBinding

class ImageFilterAdapter(private val imageFilter: List<ImageFilter>) :
    RecyclerView.Adapter<ImageFilterAdapter.ImageFilterViewHolder>() {

    //ItemContaiterFilterBinding is generated based on item container layout recycler view "item_container_filter"
    inner class ImageFilterViewHolder(val binding: ItemContaiterFilterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFilterViewHolder {
        val binding= ItemContaiterFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageFilterViewHolder(binding )
    }

    override fun onBindViewHolder(holder: ImageFilterViewHolder, position: Int) {
        with(holder){
            with (imageFilter[position]){
                binding.imageFilterPreview.setImageBitmap(filterPreview)
                binding.textFilter.text=name
            }
        }
    }

    override fun getItemCount()= imageFilter.size

}