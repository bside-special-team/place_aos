package com.special.place.proto.ui.place.register.category

import androidx.lifecycle.LiveData
import com.special.domain.entities.PlaceCategory

interface CategoryEventListener {
    val placeCategories: LiveData<List<PlaceCategory>>

    fun setCategory(category: PlaceCategory)
}