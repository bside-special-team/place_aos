package com.special.remote.models

import com.google.gson.annotations.SerializedName

data class CoordinateToAddressModel(
    val documents: List<DocumentItem>

)

data class DocumentItem(
    @SerializedName("road_address")
    val roadAddress: AddressItem,
    @SerializedName("address")
    val address: AddressItem
)

/*
    "road_address": {
        "address_name": "서울특별시 중구 충무로 70-4",
        "region_1depth_name": "서울",
        "region_2depth_name": "중구",
        "region_3depth_name": "",
        "road_name": "충무로",
        "underground_yn": "N",
        "main_building_no": "70",
        "sub_building_no": "4",
        "building_name": "",
        "zone_no": "04544"
    },
 */
data class AddressItem(
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("region_1depth_name")
    val firstAddress: String,
    @SerializedName("region_2depth_name")
    val secondAddress: String,
    @SerializedName("region_3depth_name")
    val thirdAddress: String,
    @SerializedName("road_name")
    val roadName: String?
)