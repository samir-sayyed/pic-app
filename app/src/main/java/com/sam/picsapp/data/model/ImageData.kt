package com.sam.picsapp.data.model

data class ImageData(
    val images: List<Image>,
){
    data class Image(
        val name: String,
        val url: String,
    )
}
