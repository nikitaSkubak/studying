package com.example.testapplication.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.ui.post.PostViewModel
import com.example.testapplication.usecase.post.PostUseCase
import com.example.testapplication.util.test.TestObserver
import com.example.testapplication.vo.Resource
import com.example.testapplication.vo.Status
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class PostViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val postUseCase = Mockito.mock(PostUseCase::class.java)
    private var postsObserver: Observer<Resource<List<PlaceHolderPost>>> = mock()
    lateinit var postViewModel: PostViewModel

    @Before
    @Throws
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline()}
        postViewModel = PostViewModel(postUseCase)
    }

    @Test
    fun getUsersTestNonEmptyResultSuccess() {
        val user0 = PlaceHolderPost(0, 0, "Luke", "Skywalker")
        val user1 = PlaceHolderPost(1, 1, "Leia", "Organa")
        val user2 = PlaceHolderPost(0, 2, "Han", "Solo")
        val response = listOf(user0, user1, user2)
        val postId = 0
        postViewModel.postsData.observeForever(postsObserver)

        Mockito.`when`(postUseCase.getPostsFromPlaceHolderApi(postId)).thenReturn(Single.just(response))

        postViewModel.getPosts(postId)

        TestObserver.test(postViewModel.postsData)
            .assertHasValue()
            .assertValue { it.status == Status.SUCCESS  }
            .assertValue { data -> data.data == response }
            .assertValue { data -> data.data?.size == 3 }
    }

    @Test
    fun getUsersTestNonEmptyResultError() {
        val response = Throwable()
        val postId = 0

        postViewModel.postsData.observeForever(postsObserver)

        Mockito.`when`(postUseCase.getPostsFromPlaceHolderApi(postId)).thenReturn(Single.error(response))

        postViewModel.getPosts(postId)

        TestObserver.test(postViewModel.postsData)
            .assertValue { it.status == Status.ERROR }
    }
}