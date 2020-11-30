package com.example.testapplication.repository

import com.example.testapplication.api.PlaceHolderComment
import io.reactivex.Observable
import io.reactivex.Single
import com.example.testapplication.dataBase.Comment

interface CommentRepository {
    /**
     *Returns list of PlaceHolderComment`s from PlaceHolderApi
     * @return list of PlaceHolderComment.
     **/
    fun getCommentsFromPlaceHolderApi(id: Int): Single<List<PlaceHolderComment>>
    /**
     *Inserts list of comments into DataBase.
     * @return Observable that contains list of comments id  that were successfully inserted.
     * @param comments List<Comment> to set the list into DataBase table `comments`.
     **/
    fun insertComments(comments: List<Comment>): Observable<List<Long>>
}