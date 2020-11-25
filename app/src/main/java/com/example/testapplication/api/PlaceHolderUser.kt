package com.example.testapplication.api

import com.example.testapplication.api.PlaceHolderAddress

data class PlaceHolderUser(
        val id: Int,
        val name: String,
        val username: String,
        val email: String,
        val address: PlaceHolderAddress
)