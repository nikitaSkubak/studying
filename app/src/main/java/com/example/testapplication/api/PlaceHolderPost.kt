package com.example.testapplication.api

import com.example.testapplication.dataBase.Comment

data class PlaceHolderPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
){
     var comments: List<Comment> = listOf()
}