package com.special.place.proto.ui.main

import androidx.lifecycle.*
import com.special.domain.entities.Coordinate
import com.special.domain.entities.Place
import com.special.domain.repositories.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val placeRepo: PlaceRepository) : ViewModel() {
    val places: LiveData<List<Place>> = placeRepo.places.asLiveData()

    init {
        viewModelScope.launch {


        }

    }

}