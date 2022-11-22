package com.special.domain.entities

import com.special.domain.entities.user.PointResult

data class BaseResponse<T>(
    val response: T,
    val pointResult: PointResult
)
