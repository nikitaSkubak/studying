package com.example.testapplication.api

data class PlaceHolderAddress(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: PlaceHolderGeo
) {
    override fun toString() = "$street\n$suite\n$city\n$zipcode\n${geo.toString()}"
}

