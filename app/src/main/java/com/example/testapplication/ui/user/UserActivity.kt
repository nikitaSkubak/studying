package com.example.testapplication.ui.user

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testapplication.R
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.databinding.ActivityUserBinding
import com.example.testapplication.main.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class UserActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    lateinit var userViewModel: UserViewModel
    lateinit var binding: ActivityUserBinding
    lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)

        adapter = UserListAdapter()
        binding.contentUser.rvUser.adapter = adapter

        userViewModel = injectViewModel(viewModelFactory)
        userViewModel.getUsers()
        userViewModel.users.observe(this, observer)

    }

    private val observer: Observer<List<PlaceHolderUser>> =
            Observer { users -> adapter.setUsers(users) }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }
}