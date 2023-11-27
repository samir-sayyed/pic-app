package com.sam.picsapp.data

import android.content.Context
import com.google.gson.Gson
import com.sam.picsapp.data.model.ImageData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PicRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getPics() = flow {
        context.assets?.open("images.json")?.use { inputStream ->
            try {
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                val json = String(buffer)
                val imagesData = Gson().fromJson(json, ImageData::class.java)
                emit(imagesData)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ImageData(emptyList()))
            }
        }
    }

}