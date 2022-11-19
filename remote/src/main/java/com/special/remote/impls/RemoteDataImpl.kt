package com.special.remote.impls

import com.special.domain.datasources.RemoteDataSource
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.remote.ApiManager
import com.special.remote.PlaceAppApiManager
import com.special.remote.apis.PlaceApi
import okhttp3.MediaType
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class RemoteDataImpl @Inject constructor(
    @PlaceAppApiManager apiManager: ApiManager
) : RemoteDataSource {
    private val client = apiManager.create(PlaceApi::class.java)

    override suspend fun allPlaces(): List<Place> {
        return client.allPlaces().let { it.landMarkPlaces + it.hiddenPlaces }
    }

    override suspend fun boundsPlaces(from: Coordinate, to: Coordinate): List<Place> {
        return client.coordinatePlaces(
            fromLat = from.latitude,
            fromLng = from.longitude,
            toLat = to.latitude,
            toLng = to.longitude
        )
            .let { it.landMarkPlaces + it.hiddenPlaces }
    }

    override suspend fun registerPlace(request: RequestRegisterPlace) {
        client.registerPlaces(request)
    }

    override suspend fun uploadImage(files: List<File>): List<String> {
        val builder = MultipartBody.Builder()

        val images = files
            .map {
                MultipartBody.Part.createFormData(
                    "images",
                    it.name,
                    MultipartBody.create(MediaType.parse("image/jpeg"), it)
                )
            }

        return client.uploadImage(images)
    }
}