package com.special.domain.repositories

import com.special.domain.entities.Paging
import com.special.domain.entities.place.Place
import com.special.domain.entities.place.comment.Comment

interface ArticleRepository {

    suspend fun myLikePlace(page: Int): Paging<Place>

    suspend fun myCommentList(page: Int): Paging<Comment>

    suspend fun visitedPlaces(page: Int): Paging<Place>

    suspend fun myPlaces(page: Int): Paging<Place>

    suspend fun removePlace(targetId: String)

    suspend fun removeComment(targetId: String)


    suspend fun reportComment(commentId: String)

    suspend fun reportPlace(placeId: String)
}