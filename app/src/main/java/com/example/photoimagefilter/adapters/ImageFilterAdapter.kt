package com.example.photoimagefilter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.photoimagefilter.R
import com.example.photoimagefilter.data.ImageFilter
import com.example.photoimagefilter.databinding.ItemContaiterFilterBinding
import com.example.photoimagefilter.listeners.ImageFilterListeners

class ImageFilterAdapter(
    private val imageFilter: List<ImageFilter>,
    private val imageFilterListeners: ImageFilterListeners
) :
    RecyclerView.Adapter<ImageFilterAdapter.ImageFilterViewHolder>() {

    private var selectedFilterPosition = 0
    private var previousSelectedPosition = 0


    //ItemContaiterFilterBinding is generated based on item container layout recycler view "item_container_filter"
    inner class ImageFilterViewHolder(val binding: ItemContaiterFilterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFilterViewHolder {
        val binding =
            ItemContaiterFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageFilterViewHolder, position: Int) {
        with(holder) {
            with(imageFilter[position]) {
                binding.imageFilterPreview.setImageBitmap(filterPreview)
                binding.textFilter.text = name
                binding.root.setOnClickListener {
                    if(position != selectedFilterPosition){
                        imageFilterListeners.oneFilterSelected(this)
                        previousSelectedPosition = selectedFilterPosition
                        selectedFilterPosition = position
                        with(this@ImageFilterAdapter){
                            notifyItemChanged(previousSelectedPosition, Unit)
                            notifyItemChanged(selectedFilterPosition, Unit)
                        }
                    }

                }
            }
            binding.textFilter.setTextColor(
                ContextCompat.getColor(
                    binding.textFilter.context,
                    if (selectedFilterPosition == position)
                        R.color.primaryDark
                    else
                        R.color.primaryText
                )
            )
        }
    }

    override fun getItemCount() = imageFilter.size

}