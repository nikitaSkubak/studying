package com.example.testapplication.ui.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.dataBase.Post
import com.example.testapplication.usecase.post.PostUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PostViewModel @Inject constructor(private val repository: PostUseCase): ViewModel() {
    val posts by lazy { MutableLiveData<List<PlaceHolderPost>>() }
    private val disposable = CompositeDisposable()

    fun getPosts(id: Int){
        disposable.add(repository.getPostsFromPlaceHolderApi(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listOfPosts -> posts.value = listOfPosts },
                Throwable::printStackTrace))
    }
}