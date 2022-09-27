package com.special.mock.model

import com.google.gson.annotations.SerializedName

/*
{
  "page": 0,
  "perPage": 0,
  "totalCount": 0,
  "currentCount": 0,
  "matchCount": 0,
  "data": [
    {
      "csId": 0,
      "csNm": "string",
      "addr": "string",
      "lat": "string",
      "longi": "string",
      "cpId": 0,
      "cpNm": "string",
      "chargeTp": "string",
      "cpTp": "string",
      "statUpdatetime": "string",
      "cpStat": "string"
    }
  ]
}
 */

data class PagingResponseModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("perPage")
    val size: Int,
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("currentCount")
    val currentCount: Int,
    @SerializedName("matchCount")
    val matchCount: Int,
    @SerializedName("data")
    val data: List<EvCharger>
)
