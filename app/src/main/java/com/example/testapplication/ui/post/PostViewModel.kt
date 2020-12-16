package com.example.testapplication.ui.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.usecase.post.PostUseCase
import com.example.testapplication.vo.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PostViewModel @Inject constructor(private val repository: PostUseCase) : ViewModel() {
    val postsData by lazy {
        MutableLiveData<Resource<List<PlaceHolderPost>>>()
    }
    private val disposable = CompositeDisposable()

    fun getPosts(id: Int) {
        disposable.add(repository.getPostsFromPlaceHolderApi(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { listOfPosts -> postsData.value = Resource.success(listOfPosts) },
                        { postsData.value = Resource.error(it.stackTraceToString(), null) }))
    }

}