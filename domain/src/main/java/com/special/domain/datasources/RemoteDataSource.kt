package com.special.domain.datasources

import com.special.domain.entities.Paging
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.RequestRegisterPlace
import com.special.domain.entities.user.Comment
import com.special.domain.entities.user.CommentRequest
import com.special.domain.entities.user.PointResult
import com.special.domain.entities.user.User
import java.io.File

interface RemoteDataSource {
    suspend fun allPlaces(): Result<List<Place>>

    suspend fun boundsPlaces(from: Coordinate, to: Coordinate): Result<List<Place>>

    suspend fun registerPlace(request: RequestRegisterPlace)

    suspend fun uploadImage(files: List<File>): Result<List<String>>

    suspend fun visitPlace(placeId: String): PointResult

    suspend fun recommendPlace(placeId: String): PointResult

    suspend fun registerComment(comment: CommentRequest): PointResult

    suspend fun updateNickName(nickName: String)

    suspend fun checkUser(): User

    suspend fun commentList(placeId: String, lastTimestamp: Long): Paging<Comment>
}