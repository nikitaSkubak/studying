package com.example.testapplication.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testapplication.api.PlaceHolderAddress
import com.example.testapplication.api.PlaceHolderGeo
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.repository.UserRepository
import com.example.testapplication.usecase.user.UserUseCase
import com.example.testapplication.usecase.user.impl.UserUseCaseImpl
import com.example.testapplication.util.toUser
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class UserUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val userRepo = mock(UserRepository::class.java)
    private lateinit var useCase: UserUseCase

    @Before
    @Throws
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        useCase = UserUseCaseImpl(userRepo)
    }

    @Test
    fun getUsersFromApiAndSaveThemIntoDBTestSuccess() {
        val geo = PlaceHolderGeo(25.5f, 25.5f)
        val address = PlaceHolderAddress("Groove", "5", "NY", "0000", geo)
        val user0 = PlaceHolderUser(0, "NAME1", "Luke", "Skywalker", address)
        val user1 = PlaceHolderUser(1, "NAME2", "Leia", "Organa", address)
        val user2 = PlaceHolderUser(2, "NAME3", "Han", "Solo", address)

        val anyArray = listOf(user0, user1, user2)
        val userArray = anyArray.map { it.toUser() }
        val response = listOf<Long>(0, 1, 2)

        `when`(userRepo.getUsersFromPlaceHolderApi()).thenReturn(Single.just(anyArray))
        `when`(userRepo.insertUsers(userArray)).thenReturn(Single.just(response))
        `when`(userRepo.getUsersFromDB()).thenReturn(Single.just(userArray))

        useCase.getUsersFromApiAndSaveThemIntoDB()
            .test()
            .await()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.size == 3 }
            .assertValue(userArray)
            .assertComplete()
            .dispose()
    }
}