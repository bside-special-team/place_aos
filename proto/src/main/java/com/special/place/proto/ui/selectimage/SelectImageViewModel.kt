package com.special.place.proto.ui.selectimage

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.*
import com.special.data.utils.BitmapConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SelectImageViewModel @Inject constructor() : ViewModel() {

    private val _bitmap: MutableLiveData<Bitmap> = MutableLiveData()
    private val _imageBitmapList : MutableLiveData<List<Bitmap>> = MutableLiveData()

    val imageBitmapList: LiveData<List<Bitmap>> = _imageBitmapList

    fun updateBitmap(bitmap: Bitmap) {
        viewModelScope.launch {
            val webpBitmap = BitmapConverter.convertWebpBitmap(bitmap)

            _imageBitmapList.postValue(listOf(bitmap, webpBitmap))
        }
    }
}