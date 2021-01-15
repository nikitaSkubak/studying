package com.example.testapplication.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testapplication.dataBase.User
import com.example.testapplication.ui.user.UserViewModel
import com.example.testapplication.usecase.user.UserUseCase
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import kotlin.jvm.Throws

@RunWith(JUnit4::class)
class UserViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val userUseCase = mock(UserUseCase::class.java)
    private var usersObserver: Observer<Resource<List<User>>> = mock()
    lateinit var userViewModel: UserViewModel

    @Before
    @Throws
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline()}
        userViewModel = UserViewModel(userUseCase)
    }

    @Test
    fun getUsersTestNonEmptyResultSuccess() {
        val user0 = User(0, "NAME1", "Luke", "Skywalker", "NY")
        val user1 = User(1, "NAME2", "Leia", "Organa", "NY")
        val user2 = User(2, "NAME3", "Han", "Solo", "NY")
        val response = listOf(user0, user1, user2)
        userViewModel.usersData.observeForever(usersObserver)

        `when`(userUseCase.getUsersFromApiAndSaveThemIntoDB()).thenReturn(Single.just(response))

        userViewModel.getUsers()

        TestObserver.test(userViewModel.usersData)
            .assertHasValue()
            .assertValue { it.status == Status.SUCCESS  }
            .assertValue { data -> data.data == response }
            .assertValue { data -> data.data?.size == 3 }
    }

    @Test
    fun getUsersTestNonEmptyResultError() {
        val response = Throwable()
        userViewModel.usersData.observeForever(usersObserver)

        `when`(userUseCase.getUsersFromApiAndSaveThemIntoDB()).thenReturn(Single.error(response))

        userViewModel.getUsers()

        TestObserver.test(userViewModel.usersData)
            .assertValue { it.status == Status.ERROR }
    }
}