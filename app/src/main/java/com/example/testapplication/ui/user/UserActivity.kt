package com.example.testapplication.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testapplication.R
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.dataBase.User
import com.example.testapplication.databinding.ActivityUserBinding
import com.example.testapplication.main.ViewModelProviderFactory
import com.example.testapplication.ui.post.PostActivity
import com.google.android.material.card.MaterialCardView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class UserActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityUserBinding
    private lateinit var adapter: UserListAdapter
    private var sortAscending = true
    private val REPLY = "com.example.testapplication.USER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        setSupportActionBar(binding.appBar.toolbar)

        adapter = UserListAdapter()
        binding.contentUser.rvUser.adapter = adapter

        userViewModel = injectViewModel(viewModelFactory)
        userViewModel.getUsers()
        userViewModel.users.observe(this, observer)

        with(binding.appBar) {
            btnSearchTitle.setOnClickListener {
                adapter.filter.filter(tvSearchTitle.text.toString())
            }
            btnTheme.setOnClickListener { changeTheme() }
        }
    }

    private val observer: Observer<List<User>> =
            Observer { users -> adapter.setUsers(users) }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }

    fun openPostsOfUser(v: View) {
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra(REPLY, (v as MaterialCardView).contentDescription)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.iSort -> {
                adapter.usersList = if (sortAscending)
                    adapter.usersList.sortedBy { it.username }
                else
                    adapter.usersList.sortedByDescending { it.username }
                sortAscending = !sortAscending
                binding.contentUser.rvUser.adapter = adapter
                binding.contentUser.rvUser.adapter!!.notifyDataSetChanged()
                Toast.makeText(this, "Sort", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    fun changeTheme() {
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        recreate()
    }
}