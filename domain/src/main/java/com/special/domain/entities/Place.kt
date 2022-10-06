package com.special.domain.entities

import java.time.LocalDateTime
import java.time.LocalTime

/*
{
  "createdAt": "2022-10-04T05:49:06.463Z",
  "lastModifiedAt": "2022-10-04T05:49:06.463Z",
  "id": "string",
  "placeType": "HIDDEN",
  "category": {
    "id": "string",
    "code": "string",
    "name": "string"
  },
  "coordinate": {
    "latitude": 120.12312312,
    "longitude": 20.12312312
  },
  "name": "string",
  "description": "string",
  "imageUrls": [
    "string"
  ],
  "visitCount": 0,
  "bestStartTime": "23:00",
  "bestEndTime": "23:00",
  "hashTags": [
    "string"
  ],
  "season": "SPRING"
}
 */

data class Place(
    val id : String,
    val placeType: PlaceType,
    val coordinate: Coordinate,
    val category: PlaceCategory,
    val name: String,
    val description: String,
    val imageUrls: List<String>,
    val visitCount: Int,
    val bestStartTime: String?,
    val bestEndTime: String?,
    val hashTags: List<String>,
    val season: Season?,
    val createdAt: String,
    val lastModifiedAt: String
)