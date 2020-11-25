package com.example.testapplication.api

data class PlaceHolderPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
){
    lateinit var comments: List<PlaceHolderComment>
}