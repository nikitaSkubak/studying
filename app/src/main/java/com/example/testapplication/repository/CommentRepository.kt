package com.example.testapplication.repository

import com.example.testapplication.api.PlaceHolderComment
import io.reactivex.Observable
import io.reactivex.Single
import com.example.testapplication.dataBase.Comment

interface CommentRepository {
    fun getCommentsFromPlaceHolderApi(id: Int): Single<List<PlaceHolderComment>>
    fun insertComments(comments: List<Comment>): Observable<List<Long>>
}