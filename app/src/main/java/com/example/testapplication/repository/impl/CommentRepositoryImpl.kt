package com.example.testapplication.repository.impl

import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.api.PlaceHolderComment
import com.example.testapplication.dataBase.dao.CommentDao
import com.example.testapplication.repository.CommentRepository
import io.reactivex.Observable
import io.reactivex.Single
import com.example.testapplication.dataBase.Comment
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
        private val dao: CommentDao,
        private val placeholder: PlaceHolderApi
) : CommentRepository {

    override fun getCommentsFromPlaceHolderApi(id: Int): Single<List<PlaceHolderComment>> =
            placeholder.getComments(id)

    override fun insertComments(comments: List<Comment>) = dao.insertComments(comments)
}