package com.sam.picsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sam.picsapp.data.model.ImageData
import com.sam.picsapp.databinding.ImageItemBinding

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    private var imageList = listOf<ImageData.Image>()

    fun updateList(list: List<ImageData.Image>) {
        imageList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val viewBinding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imageList[position]
        Glide.with(holder.itemView.context).load(image.url).into(holder.image)
        holder.imageName.text = image.name
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ImageViewHolder(imageItemBinding: ImageItemBinding): RecyclerView.ViewHolder(imageItemBinding.root){
        val image = imageItemBinding.imageView
        val imageName = imageItemBinding.imageName
    }
}