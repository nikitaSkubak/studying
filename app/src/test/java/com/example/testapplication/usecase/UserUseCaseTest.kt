package com.example.testapplication.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testapplication.api.PlaceHolderAddress
import com.example.testapplication.api.PlaceHolderGeo
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.dataBase.User
import com.example.testapplication.repository.UserRepository
import com.example.testapplication.usecase.user.impl.FakeUserUseCase
import com.example.testapplication.util.toUser
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class UserUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val userRepo = mock(UserRepository::class.java)
    private lateinit var useCase: FakeUserUseCase

    @Before
    @Throws
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        useCase = FakeUserUseCase(userRepo)
    }

    @Test
    fun getUsersFromApiAndSaveThemIntoDBTestSuccess() {
        val geo = PlaceHolderGeo(25.5f, 25.5f)
        val address = PlaceHolderAddress("Groove", "5", "NY", "0000", geo)
        val user0 = PlaceHolderUser(0, "NAME1", "Luke", "Skywalker", address)
        val user1 = PlaceHolderUser(1, "NAME2", "Leia", "Organa", address)
        val user2 = PlaceHolderUser(2, "NAME3", "Han", "Solo", address)

        val User0 = User(0, "NAME1", "Luke", "Skywalker", "NY")
        val User1 = User(1, "NAME2", "Leia", "Organa", "NY")
        val User2 = User(2, "NAME3", "Han", "Solo", "NY")

        val anyArray = listOf(user0, user1, user2)
        val userArray = listOf(User0, User1, User2)
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

    @Test
    fun getUsersFromApiAndSaveThemIntoDBTestEmptySuccess() {

        val anyArray = listOf<PlaceHolderUser>()
        val userArray = listOf<User>()
        val response = listOf<Long>()

        `when`(userRepo.getUsersFromPlaceHolderApi()).thenReturn(Single.just(anyArray))
        `when`(userRepo.insertUsers(userArray)).thenReturn(Single.just(response))
        `when`(userRepo.getUsersFromDB()).thenReturn(Single.just(userArray))

        useCase.getUsersFromApiAndSaveThemIntoDB()
            .test()
            .await()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.isEmpty() }
            .assertValue(userArray)
            .assertComplete()
            .dispose()
    }

    @Test
    fun getUsersFromApiAndSaveThemIntoDBTestError() {

        val anyArray = listOf<PlaceHolderUser>()
        val userArray = listOf<User>()
        val response = Throwable()

        `when`(userRepo.getUsersFromPlaceHolderApi()).thenReturn(Single.just(anyArray))
        `when`(userRepo.insertUsers(userArray)).thenReturn(Single.error(response))
        `when`(userRepo.getUsersFromDB()).thenReturn(Single.just(userArray))

        useCase.getUsersFromApiAndSaveThemIntoDB()
            .test()
            .await()
            .assertSubscribed()
            .assertError(response)
            .assertNotComplete()
            .dispose()
    }

    @Test
    fun getUsersFromApiDBTestNotEmptySuccess() {
        val geo = PlaceHolderGeo(25.5f, 25.5f)
        val address = PlaceHolderAddress("Groove", "5", "NY", "0000", geo)
        val user0 = PlaceHolderUser(0, "NAME1", "Luke", "Skywalker", address)
        val user1 = PlaceHolderUser(1, "NAME2", "Leia", "Organa", address)
        val user2 = PlaceHolderUser(2, "NAME3", "Han", "Solo", address)

        val anyArray = listOf(user0, user1, user2)

        `when`(userRepo.getUsersFromPlaceHolderApi()).thenReturn(Single.just(anyArray))

        useCase.getUsersFromApi()
            .test()
            .await()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.size == 3 }
            .assertValue(anyArray)
            .assertComplete()
            .dispose()
    }

    @Test
    fun getUsersFromApiDBTestEmptySuccess() {

        val anyArray = listOf<PlaceHolderUser>()

        `when`(userRepo.getUsersFromPlaceHolderApi()).thenReturn(Single.just(anyArray))

        useCase.getUsersFromApi()
            .test()
            .await()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.isEmpty() }
            .assertValue(anyArray)
            .assertComplete()
            .dispose()
    }

    @Test
    fun getUsersFromApiDBTestError() {

        val response = Throwable()

        `when`(userRepo.getUsersFromPlaceHolderApi()).thenReturn(Single.error(response))

        useCase.getUsersFromApi()
            .test()
            .await()
            .assertSubscribed()
            .assertError(response)
            .assertNotComplete()
            .dispose()
    }

    @Test
    fun insertUsersIntoDbTestNotEmptySuccess() {

        val user0 = User(0, "NAME1", "Luke", "Skywalker", "NY")
        val user1 = User(1, "NAME2", "Leia", "Organa", "NY")
        val user2 = User(2, "NAME3", "Han", "Solo", "NY")

        val anyArray = listOf(user0, user1, user2)
        val response = listOf<Long>(0, 1, 2)

        `when`(userRepo.insertUsers(anyArray))
            .thenReturn(Single.just(response))

        useCase.insertUsersIntoDb(anyArray)
            .test()
            .await()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.size == 3 }
            .assertValue(response)
            .assertComplete()
            .dispose()
    }

    @Test
    fun insertUsersIntoDbTestError() {

        val user0 = User(0, "NAME1", "Luke", "Skywalker", "NY")
        val user1 = User(1, "NAME2", "Leia", "Organa", "NY")
        val user2 = User(2, "NAME3", "Han", "Solo", "NY")

        val anyArray = listOf(user0, user1, user2)
        val response = Throwable()

        `when`(userRepo.insertUsers(anyArray))
            .thenReturn(Single.error(response))

        useCase.insertUsersIntoDb(anyArray)
            .test()
            .await()
            .assertSubscribed()
            .assertError(response)
            .assertNotComplete()
            .dispose()
    }
}