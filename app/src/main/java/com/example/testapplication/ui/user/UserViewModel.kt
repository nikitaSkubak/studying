package com.example.testapplication.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.usecase.user.UserUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    val users by lazy { MutableLiveData<List<PlaceHolderUser>>() }
    private val disposable = CompositeDisposable()
    fun getUsers() {
        disposable.add(
                userUseCase.getUsers()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { listOfUsers -> users.value = listOfUsers },
                                Throwable::printStackTrace
                        )
        )
    }
}