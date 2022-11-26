package com.special.place.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.special.place.ui.Route

interface RouteListener {
    val route: LiveData<Route>
    fun requestRoute(route: Route)

    companion object {
        fun empty() = object : RouteListener {
            override val route: LiveData<Route>
                get() = MutableLiveData()

            override fun requestRoute(route: Route) {

            }

        }
    }
}