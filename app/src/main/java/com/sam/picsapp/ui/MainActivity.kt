package com.sam.picsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselStrategy
import com.sam.picsapp.data.model.ImageData
import com.sam.picsapp.databinding.ActivityMainBinding
import com.sam.picsapp.ui.adapter.CarouselAdapter
import com.sam.picsapp.ui.adapter.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var imageList = listOf<ImageData.Image>()

    private var viewBinding: ActivityMainBinding? = null

    private var imageAdapter: ImageAdapter? = null

    private val viewModel: PicViewModel by lazy {
        ViewModelProvider(this)[PicViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)
        imageAdapter = ImageAdapter()
        viewModel.getImages()
        viewModel.imageData.observe(this) { imageData ->
            imageList = imageData
            imageAdapter?.updateList(imageData)
            viewBinding?.imageListRecyclerView?.adapter = imageAdapter
            val carouselAdapter = CarouselAdapter(imageData)
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            viewBinding?.carousel?.layoutManager = layoutManager
            viewBinding?.carousel?.adapter = carouselAdapter
        }
        filterTenants()
    }

    private fun filterTenants() {
        var textChanged = false
        viewBinding?.searchImages?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textChanged = true
            }

            override fun afterTextChanged(s: Editable?) {
                if (textChanged) {
                    filter(s.toString())
                    textChanged = false
                }
            }
        })
    }

    private fun filter(text: String?) {
        val list =
            imageList.filter { image ->
                text?.lowercase()?.let { image.name.lowercase().startsWith(it) }?: false
        }
        viewBinding?.imageListRecyclerView?.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        viewBinding?.noResultsFound?.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        imageAdapter?.updateList(list)
        viewBinding?.imageListRecyclerView?.scrollToPosition(0)
    }
}