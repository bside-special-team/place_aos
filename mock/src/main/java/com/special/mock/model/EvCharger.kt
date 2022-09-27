package com.special.mock.model

import com.google.gson.annotations.SerializedName

/*
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
 */
data class EvCharger(
    @SerializedName("csId")
    val id: Long,
    @SerializedName("csNm")
    val csNm: String,
    @SerializedName("addr")
    val address: String,
    @SerializedName("lat")
    val latitude: String,
    @SerializedName("longi")
    val longitude: String,
    @SerializedName("cpId")
    val cpId: String,
    @SerializedName("cpNm")
    val name: String,
    @SerializedName("chargeTp")
    val chargerTp: String,
    @SerializedName("cpTp")
    val cpTp: String,
    @SerializedName("statUpdatetime")
    val updateDate: String,
    @SerializedName("cpStat")
    val stat: String,
)
