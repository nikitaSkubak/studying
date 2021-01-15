package com.example.testapplication.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.dataBase.User
import com.example.testapplication.usecase.user.UserUseCase
import com.example.testapplication.vo.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    val usersData by lazy { MutableLiveData<Resource<List<User>>>() }
    private val disposable = CompositeDisposable()

    fun getUsers() {
        disposable.add(
                userUseCase.getUsersFromApiAndSaveThemIntoDB()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { listOfUsers -> usersData.value = Resource.success(listOfUsers) },
                                {
                                    usersData.value = Resource.error(
                                            it.stackTrace.toString(),
                                            null)
                                }))
    }
}