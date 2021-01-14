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


}