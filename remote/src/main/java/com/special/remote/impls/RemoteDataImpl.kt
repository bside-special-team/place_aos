package com.special.remote.impls

import com.special.domain.datasources.RemoteDataSource
import com.special.domain.datasources.TokenDataSource
import com.special.domain.entities.Paging
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.PlaceResponse
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.entities.place.comment.Comment
import com.special.domain.entities.place.comment.CommentRequest
import com.special.domain.entities.user.LevelInfo
import com.special.domain.entities.user.NickNameUpdate
import com.special.domain.entities.user.PointResult
import com.special.domain.entities.user.User
import com.special.remote.ApiManager
import com.special.remote.PlaceAppApiManager
import com.special.remote.apis.PlaceApi
import okhttp3.MediaType
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class RemoteDataImpl @Inject constructor(
    @PlaceAppApiManager apiManager: ApiManager,
    private val tokenData: TokenDataSource
) : RemoteDataSource {
    private val client = apiManager.create(PlaceApi::class.java)

    override suspend fun allPlaces(): Result<List<Place>> {
        return tokenData.checkToken {
            client.allPlaces().let { it.landMarkList + it.hiddenPlaceList }
        }
    }

    override suspend fun boundsPlaces(from: Coordinate, to: Coordinate): Result<PlaceResponse> {
        return tokenData.checkToken {
            client.coordinatePlaces(
                fromLat = from.latitude,
                fromLng = from.longitude,
                toLat = to.latitude,
                toLng = to.longitude
            )
        }
    }

    override suspend fun registerPlace(request: RequestRegisterPlace) {
        client.registerPlaces(request)
    }

    override suspend fun uploadImage(files: List<File>): Result<List<String>> {
        return tokenData.checkToken {
            val images = files
                .map {
                    MultipartBody.Part.createFormData(
                        "images",
                        it.name,
                        MultipartBody.create(MediaType.parse("image/jpeg"), it)
                    )
                }

            client.uploadImage(images)
        }
    }

    override suspend fun visitPlace(placeId: String): PointResult {
        return client.visitPlace(placeId).pointResult
    }

    override suspend fun recommendPlace(placeId: String): PointResult {
        return client.recommendPlace(placeId).pointResult
    }

    override suspend fun registerComment(comment: CommentRequest): PointResult {
        return client.registerComments(comment).pointResult
    }

    override suspend fun updateNickName(nickName: String) {
        return client.updateNickname(NickNameUpdate(nickName))
    }

    override suspend fun checkUser(): User {
        return client.checkUser().response
    }

    override suspend fun commentList(placeId: String, lastTimestamp: Long): Paging<Comment> {
        return client.placeComments(placeId, lastTimestamp, limit = 15).let {
            Paging(isLast = !it.hasNext, list = it.comments)
        }
    }

    override suspend fun levelInfo(): List<LevelInfo> {
        return client.levelInfo()
    }
}