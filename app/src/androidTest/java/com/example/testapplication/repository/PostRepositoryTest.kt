package com.example.testapplication.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.dataBase.Post
import com.example.testapplication.dataBase.TwitRoomDataBase
import com.example.testapplication.dataBase.dao.PostDao
import com.example.testapplication.repository.impl.PostRepositoryImpl
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class PostRepositoryTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val postDao = mock(PostDao::class.java)
    private val placeHolderApi = mock(PlaceHolderApi::class.java)
    lateinit var db: TwitRoomDataBase
    lateinit var postRepository: PostRepository

    @Before
    fun setup() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        db = Room.inMemoryDatabaseBuilder(context, TwitRoomDataBase::class.java).build()
        postRepository = PostRepositoryImpl(postDao, placeHolderApi)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertPostsIntoDbTestSuccess() {

        val post0 = Post(0, 0, "Post1", "Skywalker")
        val post1 = Post(0, 1, "Post2", "Skywalker")
        val post2 = Post(1, 2, "Post1", "Organa")

        val postsList = listOf(post0, post1, post2)
        val response: List<Long> = listOf(0, 1, 2)

        Mockito.`when`(postDao.insertPosts(postsList)).thenReturn(Single.just(response))

        postRepository.insertPosts(postsList)
            .test()
            .assertSubscribed()
            .assertValue { it.isNotEmpty() }
            .assertValue { it.size == 3 }
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }
    @Test
    fun insertPostsIntoDbTestError() {

        val post0 = Post(0, 0, "Post1", "Skywalker")
        val post1 = Post(0, 1, "Post2", "Skywalker")
        val post2 = Post(1, 2, "Post1", "Organa")
        val postsList = listOf(post0, post1, post2)
        val response = Throwable()

        Mockito.`when`(postDao.insertPosts(postsList)).thenReturn(Single.error(response))

        postRepository.insertPosts(postsList)
            .test()
            .assertSubscribed()
            .assertError(response)
            .assertNotComplete()
            .dispose()
    }


    @Test
    fun getPostsFromPlaceHolderApiTestNotEmptySuccess() {

        val post0 = PlaceHolderPost(0, 0, "Post1", "Skywalker")
        val post1 = PlaceHolderPost(0, 1, "Post2", "Skywalker")
        val post2 = PlaceHolderPost(1, 2, "Post1", "Organa")

        val response = listOf(post0, post1, post2)

        Mockito.`when`(placeHolderApi.getPosts())
            .thenReturn(Single.just(response))

        postRepository.getPostsFromPlaceHolderAPi()
            .test()
            .assertSubscribed()
            .assertValue { it.isNotEmpty() }
            .assertValue { it.size == 3 }
            .assertValue { it[0].title == "Post1" }
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }

    @Test
    fun getPostsFromPlaceHolderApiTestEmptySuccess() {

        val response = listOf<PlaceHolderPost>()

        Mockito.`when`(placeHolderApi.getPosts()).thenReturn(Single.just(response))

        postRepository.getPostsFromPlaceHolderAPi()
            .test()
            .assertSubscribed()
            .assertValue { it.isEmpty() }
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }

    @Test
    fun getPostsFromPlaceHolderApiTestError() {

        val response = Throwable()

        Mockito.`when`(placeHolderApi.getPosts()).thenReturn(Single.error(response))

        postRepository.getPostsFromPlaceHolderAPi()
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertSubscribed()
            .assertError(response)
            .assertNotComplete()
            .dispose()
    }
}