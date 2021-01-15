package com.example.testapplication.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testapplication.api.PlaceHolderAddress
import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.api.PlaceHolderGeo
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.dataBase.TwitRoomDataBase
import com.example.testapplication.dataBase.User
import com.example.testapplication.dataBase.dao.UserDao
import com.example.testapplication.repository.impl.UserRepositoryImpl
import com.example.testapplication.util.toUser
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class UserRepositoryTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val userDao = Mockito.mock(UserDao::class.java)
    private val placeHolderApi = Mockito.mock(PlaceHolderApi::class.java)
    lateinit var db: TwitRoomDataBase
    private val geo = PlaceHolderGeo(25.5f, 25.5f)
    val address = PlaceHolderAddress( "Groove","5","NY","0000", geo)
    val user0 = PlaceHolderUser(0, "NAME1", "Luke", "Skywalker", address)
    val user1 = PlaceHolderUser(1, "NAME2", "Leia", "Organa", address)
    val user2 = PlaceHolderUser(2, "NAME3", "Han", "Solo", address)
    private val response = listOf(user0, user1, user2)
    lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        db = Room.inMemoryDatabaseBuilder(context, TwitRoomDataBase::class.java).build()
        userRepository = UserRepositoryImpl(userDao, placeHolderApi)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getUsersFromPlaceHolderApiTestNotEmptySuccess() {

        Mockito.`when`(placeHolderApi.getUsers()).thenReturn(Single.just(response))

        userRepository.getUsersFromPlaceHolderApi()
            .test()
            .assertSubscribed()
            .assertValue {it.isNotEmpty() }
            .assertValue { it[0].name == "NAME1"}
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }

    @Test
    fun getUsersFromPlaceHolderApiTestEmptySuccess() {

        Mockito.`when`(placeHolderApi.getUsers()).thenReturn(Single.just(listOf()))

        userRepository.getUsersFromPlaceHolderApi()
            .test()
            .assertSubscribed()
            .assertValue {it.isEmpty() }
            .assertNoErrors()
            .assertComplete()
            .dispose()
    }

    @Test
    fun getUsersFromPlaceHolderApiTestError() {

        val response = Throwable()

        Mockito.`when`(placeHolderApi.getUsers()).thenReturn(Single.error(response))

        userRepository.getUsersFromPlaceHolderApi()
            .test()
            .assertSubscribed()
            .assertError(response)
            .assertNotComplete()
            .dispose()
    }

    @Test
    fun insertUsersIntoDBTestSuccess() {
        val anyArray = response.map { it.toUser() }
        val response = listOf<Long>(0, 1, 2)

        Mockito.`when`(userDao.insertUsers(anyArray)).thenReturn(Single.just(response))

        userRepository.insertUsers(anyArray)
                .test()
                .assertSubscribed()
                .assertValue { it.isNotEmpty()}
                .assertValue { it.size == 3}
                .assertNoErrors()
                .assertComplete()
                .dispose()
    }

    @Test
    fun insertUsersIntoDBTestError() {
        val anyArray = listOf<User>()
        val response = Throwable()

        Mockito.`when`(userDao.insertUsers(anyArray)).thenReturn(Single.error(response))

        userRepository.insertUsers(anyArray)
            .test()
            .assertSubscribed()
            .assertError(response)
            .assertNotComplete()
            .dispose()
    }


}