package com.special.remote.impls

import com.special.domain.datasources.CoordinateToAddressDataSource
import com.special.domain.entities.Coordinate
import com.special.remote.ApiManager
import com.special.remote.KakaoApiManager
import com.special.remote.apis.KakaoApi
import javax.inject.Inject

class CoordinateToAddressRemoteImpl @Inject constructor(@KakaoApiManager apiManager: ApiManager) : CoordinateToAddressDataSource {
    private val client = apiManager.create(KakaoApi::class.java)

    override suspend fun coordinateToAddress(coordinate: Coordinate): String {
        return client.coord2Address(coordinate.longitude, coordinate.latitude).let {
            if (it.documents.isEmpty()) {
                return@let coordinate.toString()
            }

            val firstItem = it.documents.first()

            if (firstItem.roadAddress != null) {
                return@let firstItem.roadAddress.addressName
            }

            firstItem.address?.addressName ?: coordinate.toString()
        }

    }
}