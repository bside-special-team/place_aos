package com.special.domain.entities.place

/*
{
  "createdAt": "2022-11-19T16:14:55.681Z",
  "lastModifiedAt": "2022-11-19T16:14:55.681Z",
  "id": "string",
  "writer": {
    "id": "string",
    "authProvider": "KAKAO",
    "subject": "string",
    "email": "string",
    "nickName": "string",
    "visitInfos": [
      {
        "id": "string",
        "visitedAt": "2022-11-19T16:14:55.681Z"
      }
    ],
    "recPlaces": [
      "string"
    ]
  },
  "placeType": "HIDDEN",
  "coordinate": {
    "latitude": 120.12312312,
    "longitude": 20.12312312
  },
  "name": "string",
  "imageUrls": [
    "string"
  ],
  "visitCount": 0,
  "hashTags": [
    "string"
  ],
  "visitInfos": [
    {
      "id": "string",
      "visitedAt": "2022-11-19T16:14:55.681Z"
    }
  ],
  "recommendUsers": [
    "string"
  ]
}
 */

data class Place(
    val id: String,
    val placeType: PlaceType,
    val coordinate: Coordinate,
    val name: String,
    val imageUuids: List<String>,
    val visitCount: Int,
    val hashTags: List<String>,
    val createdAt: String,
    val lastModifiedAt: String,
    val recommendCount: Int,
    val nickName: String
) {
    companion object {
        fun mock() = Place(
            id = "id",
            placeType = PlaceType.Hidden,
            coordinate = Coordinate("37.5666102", "126.9783881"),
            name = "목업 플레이스",
            imageUuids = listOf(),
            visitCount = 0,
            hashTags = listOf(),
            createdAt = "",
            lastModifiedAt = "",
            recommendCount = 0,
            nickName = "방방방"
        )
    }
}