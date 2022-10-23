package com.special.place.proto.ui.place

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.*
import com.naver.maps.map.overlay.OverlayImage
import com.special.data.utils.BitmapConverter
import com.special.domain.entities.Coordinate
import com.special.domain.entities.Place
import com.special.domain.repositories.PlaceRepository
import com.special.place.resource.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val placeRepo: PlaceRepository) : ViewModel() {
    val places: LiveData<List<Place>> = placeRepo.places.asLiveData()

    private lateinit var baseBitmap: Bitmap
    private var lastCoordinate: Coordinate = Coordinate("0", "0")

    private val _localMarker: MutableLiveData<List<LocalMarker>> = MutableLiveData()
    val localMarkers: LiveData<List<LocalMarker>> = _localMarker

    private val imageMaps: MutableMap<Int, OverlayImage> = mutableMapOf()

    init {
        viewModelScope.launch {


        }

    }

    fun initContext(context: Context) {
        baseBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_marker_base)
    }

    fun updateCameraPosition(coordinate: Coordinate) {
        lastCoordinate = coordinate
    }

    fun updateBitmap(bitmap: Bitmap) {
        viewModelScope.launch {
            val mergedOverlay = getMergedOverlay(bitmap)

            val origin = _localMarker.value ?: listOf()

            _localMarker.postValue(origin.plus(mergedOverlay to lastCoordinate))
        }

    }

    private suspend fun getMergedOverlay(bitmap: Bitmap): OverlayImage {
        return imageMaps[bitmap.hashCode()] ?: run {
            val mergedBitmap = BitmapConverter.mergedMarker(baseBitmap, bitmap)
            val overlayImage = OverlayImage.fromBitmap(mergedBitmap)
            imageMaps[bitmap.hashCode()] = overlayImage

            overlayImage
        }
    }
}

typealias LocalMarker = Pair<OverlayImage, Coordinate>