package com.special.place.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.special.place.ui.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor() : ViewModel(), RouteListener {
    private val _route: MutableLiveData<Route> = MutableLiveData()
    override val route: LiveData<Route> = _route

    override fun requestRoute(route: Route) {
        _route.postValue(route)
    }
}