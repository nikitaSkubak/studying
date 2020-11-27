package com.example.testapplication.repository

import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.dataBase.Post
import io.reactivex.Observable
import io.reactivex.Single

interface PostRepository {
    fun getPosts(): Single<List<PlaceHolderPost>>
    fun insertPosts(posts: List<Post>): Observable<List<Long>>
    fun convertListOFPlaceHolderPostToListOfPost(listOfPlaceHolderPost: List<PlaceHolderPost>): List<Post>
}