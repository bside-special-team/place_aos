package com.special.domain.entities

data class Paging<T>(
    val isLast: Boolean,
    val list: List<T>
)
