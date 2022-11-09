package com.special.place.proto.ui.place

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.core.graphics.applyCanvas
import androidx.lifecycle.*
import com.naver.maps.map.overlay.OverlayImage
import com.special.data.utils.BitmapConverter
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.Place
import com.special.domain.repositories.PlaceRepository
import com.special.place.resource.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val placeRepo: PlaceRepository) : ViewModel() {
    val places: LiveData<List<Place>> = placeRepo.places.asLiveData()

    private lateinit var baseBitmap: Bitmap
    private lateinit var decoratedBitmap: Bitmap
    private var lastCoordinate: Coordinate = Coordinate("0", "0")

    private val _localMarker: MutableLiveData<List<LocalMarker>> = MutableLiveData()
    val localMarkers: LiveData<List<LocalMarker>> = _localMarker

    private val imageMaps: MutableMap<Int, OverlayImage> = mutableMapOf()

    init {
        viewModelScope.launch {


        }

    }

    fun initContext(context: Context) {
        val baseDrawable = ContextCompat.getDrawable(context, R.drawable.ic_landmark_background) ?: return
        val decoDrawable = ContextCompat.getDrawable(context, R.drawable.ic_landmark_badge) ?: return

        baseBitmap = Bitmap.createBitmap(baseDrawable.intrinsicWidth, baseDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888).applyCanvas {
            baseDrawable.setBounds(0, 0, width, height)
            baseDrawable.draw(this)
        }

        decoratedBitmap = Bitmap.createBitmap(decoDrawable.intrinsicWidth, decoDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888).applyCanvas {
            decoDrawable.setBounds(0, 0, width, height)
            decoDrawable.draw(this)
        }
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
            val mergedBitmap = BitmapConverter.mergedMarker(baseBitmap, decoratedBitmap, bitmap)
            val overlayImage = OverlayImage.fromBitmap(mergedBitmap)
            imageMaps[bitmap.hashCode()] = overlayImage

            overlayImage
        }
    }
}

typealias LocalMarker = Pair<OverlayImage, Coordinate>