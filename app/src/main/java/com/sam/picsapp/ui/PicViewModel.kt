package com.sam.picsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.picsapp.data.PicRepository
import com.sam.picsapp.data.model.ImageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PicViewModel @Inject constructor(
    private val picRepository: PicRepository
): ViewModel() {

    private val _imageData = MutableLiveData<List<ImageData.Image>>()
    val imageData: LiveData<List<ImageData.Image>> = _imageData


    fun getImages() = viewModelScope.launch(Dispatchers.IO) {
        picRepository.getPics().collectLatest { imageData ->
            withContext(Dispatchers.Main) {
                _imageData.value = imageData.images
            }
        }
    }

}