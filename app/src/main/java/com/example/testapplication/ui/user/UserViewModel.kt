package com.example.testapplication.ui.user

import androidx.lifecycle.ViewModel
import com.example.testapplication.usecase.user.UserUseCase
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {
}