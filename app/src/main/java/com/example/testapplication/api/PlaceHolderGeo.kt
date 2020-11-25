package com.example.testapplication.api

data class PlaceHolderGeo(
    val lat: Float,
    val lng: Float
) {
    override fun toString() = "lat: $lat\nlng: $lng"
}

