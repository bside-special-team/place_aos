package com.special.domain.entities.place

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
    val id: String,
    val placeType: PlaceType,
    val coordinate: Coordinate,
    val name: String,
    val imageUrls: List<String>,
    val visitCount: Int,
    val hashTags: List<String>,
    val createdAt: String,
    val lastModifiedAt: String
) {
    companion object {
        fun mock() = Place(
            id = "id",
            placeType = PlaceType.Hidden,
            coordinate = Coordinate("37.5666102", "126.9783881"),
            name = "목업 플레이스",
            imageUrls = listOf(),
            visitCount = 0,
            hashTags = listOf(),
            createdAt = "",
            lastModifiedAt = ""
        )
    }
}