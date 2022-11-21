package com.special.place.util

import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LocationFactory @Inject constructor(
    @ActivityContext activityContext: Context
) {
    private var _lastLocation: Location? = null

    fun lastLatLng(): LatLng? = _lastLocation?.let { LatLng(it.latitude, it.longitude) }
    fun lastLocation(): Location? = _lastLocation

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            activityContext
        )
    }

    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                _lastLocation = locationResult.lastLocation
            }
        }
    }

    private val requestLocation: LocationRequest by lazy {
        LocationRequest.create().apply {
            interval = 10_000
            // 사용자가 움직일때 마커의 위치를 추적 하기위해 인터벌 추가
            // 너무 자주 사용하지 않도록 10초로 제한

            // fastestInterval = 5_000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            // https://developer.android.com/training/location/change-location-settings
        }
    }

    fun startLocationTracking() {
        fusedLocationClient.requestLocationUpdates(
            requestLocation,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stopLocationTracking() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}