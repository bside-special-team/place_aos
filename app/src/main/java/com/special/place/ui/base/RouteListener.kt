package com.special.place.ui.base

import androidx.lifecycle.LiveData
import com.special.place.ui.Route

interface RouteListener {
    val route: LiveData<Route>
    fun requestRoute(route: Route)
}