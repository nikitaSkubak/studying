package com.example.testapplication.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.api.PlaceHolderComment
import com.example.testapplication.dataBase.Comment
import com.example.testapplication.dataBase.TwitRoomDataBase
import com.example.testapplication.dataBase.dao.CommentDao
import com.example.testapplication.repository.impl.CommentRepositoryImpl
import com.example.testapplication.util.toComment
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class CommentRepositoryImplTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val commentDao = Mockito.mock(CommentDao::class.java)
    private val placeHolderApi = Mockito.mock(PlaceHolderApi::class.java)
    lateinit var db: TwitRoomDataBase
    lateinit var commentRepository: CommentRepository
    private val comment0 = PlaceHolderComment(0, 1, "NAME1", "@gmail.com", "BODY1")
    private val comment1 = PlaceHolderComment(0, 2, "NAME2", "@gmail.com", "BODY2")
    private val comment2 = PlaceHolderComment(1, 3, "NAME3", "@gmail.com", "BODY3")
    val anyArray = listOf(comment0, comment1, comment2)

    @Before
    fun setup() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        db = Room.inMemoryDatabaseBuilder(context, TwitRoomDataBase::class.java).build()
        commentRepository = CommentRepositoryImpl(commentDao, placeHolderApi)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getCommentsFromPlaceHolderApiTestNotEmptySuccess() {

        val response = anyArray
        val postId = 0

        Mockito.`when`(placeHolderApi.getComments(postId)).thenReturn(Single.just(response))

        commentRepository.getCommentsFromPlaceHolderApi(postId)
            .test()
            .assertSubscribed()
            .assertValue { it.isNotEmpty() }
            .assertValue { it.size == 3 }
            .assertValue { it[0].name == "NAME1" }
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }

    @Test
    fun getCommentsFromPlaceHolderApiTestEmptySuccess() {

        val response = listOf<PlaceHolderComment>()
        val postId = 0

        Mockito.`when`(placeHolderApi.getComments(postId)).thenReturn(Single.just(response))

        commentRepository.getCommentsFromPlaceHolderApi(postId)
            .test()
            .assertSubscribed()
            .assertValue { it.isEmpty() }
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }

    @Test
    fun getCommentsFromPlaceHolderApiTestError() {

        val response = Throwable()
        val postId = 0

        Mockito.`when`(placeHolderApi.getComments(postId)).thenReturn(Single.error(response))

        commentRepository.getCommentsFromPlaceHolderApi(postId)
            .test()
            .assertSubscribed()
            .assertError(response)
            .assertNotComplete()
            .dispose()
    }

    @Test
    fun insertCommentsTestSuccess() {

        val anyArray = this.anyArray.map { it.toComment() }
        val response = listOf<Long>(0, 1, 2)

        Mockito.`when`(commentDao.insertComments(anyArray)).thenReturn(Single.just(response))

        commentRepository.insertComments(anyArray)
            .test()
            .assertSubscribed()
            .assertValue { it.isNotEmpty() }
            .assertValue { it.size == 3 }
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }



    @Test
    fun insertCommentsTestError() {

        val anyArray = listOf<Comment>()
        val response = Throwable()

        Mockito.`when`(commentDao.insertComments(anyArray)).thenReturn(Single.error(response))

        commentRepository.insertComments(anyArray)
            .test()
            .assertSubscribed()
            .assertError(response)
            .assertNotComplete()
            .dispose()
    }
}