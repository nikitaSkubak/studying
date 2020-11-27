package com.example.testapplication.repository

import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.dataBase.Post
import io.reactivex.Observable
import io.reactivex.Single

interface PostRepository {
    /**
     *Returns list of PlaceHolderPost`s from PlaceHolderApi response.
     *@return List<PlaceHolderPost> that contains posts.
     **/
    fun getPostsFromPlaceHolderAPi(): Single<List<PlaceHolderPost>>
    /**
     *Inserts list of Posts into DataBase table `post`.
     * @return Observable that contains list of post`s id that were successfully inserted.
     * @param posts List<Post> to set the list of posts into DataBase table `post`.
     **/
    fun insertPosts(posts: List<Post>): Observable<List<Long>>
}