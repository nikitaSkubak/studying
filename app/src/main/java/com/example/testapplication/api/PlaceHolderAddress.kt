package com.example.testapplication.api

class PlaceHolderAddress(
        val street: String,
        val suit: String,
        val city: String,
        val zipcode: String,
        val geo: PlaceHolderGeo
)
{
    override fun toString() = "$street, $suit, $city, $zipcode\n${geo.toString()}"
}
/*
"address": {
      "street": "Kulas Light",
      "suite": "Apt. 556",
      "city": "Gwenborough",
      "zipcode": "92998-3874",
      "geo": {
        "lat": "-37.3159",
        "lng": "81.1496"
      }
 */
