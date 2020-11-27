package com.example.testapplication.usecase.post

import com.example.testapplication.api.PlaceHolderPost
import io.reactivex.Single

interface PostUseCase {
    /**
     * Returns list of PlaceHolderPost from PlaceHolderApi after:
     * • inserting converted to list of Post Objects into DataBase;
     * • getting list of Comments for each PlaceHolderPost from PlaceHolderApi and inserting
     *   received list of comments into DataBase table `comment`.
     * @return Single that contains list of User`s.
     **/
    fun getPostsFromPlaceHolderApi(id: Int): Single<List<PlaceHolderPost>>
}