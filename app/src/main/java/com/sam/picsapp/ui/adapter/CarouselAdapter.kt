package com.sam.picsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sam.picsapp.data.model.ImageData
import com.sam.picsapp.databinding.CarouselItemBinding


class CarouselAdapter(
    private val images: List<ImageData.Image>
): RecyclerView.Adapter<CarouselAdapter.ImageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val viewBinding = CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        Glide.with(holder.itemView.context).load(image.url).into(holder.image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder(imageItemBinding: CarouselItemBinding): RecyclerView.ViewHolder(imageItemBinding.root){
        val image = imageItemBinding.imageView
    }
}