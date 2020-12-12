package com.example.testapplication.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testapplication.dataBase.User
import com.example.testapplication.databinding.FragmentUserBinding
import com.example.testapplication.main.ViewModelProviderFactory
import com.example.testapplication.vo.Resource
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.seacrh_layout.view.*
import javax.inject.Inject

class UserFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentUserBinding
    private lateinit var adapter: UserListAdapter
    private var sortAscending = true

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater, container, false)

        adapter = UserListAdapter()
        binding.contentUser.rvUser.adapter = adapter

        userViewModel = injectViewModel(viewModelFactory)
        userViewModel.getUsers()
        userViewModel.users.observe(viewLifecycleOwner, observer)

        with(binding.lSearch) {
            btnSearchTitle.setOnClickListener {
                adapter.filter.filter(tvSearchTitle.text.toString())
            }
            btnSort.setOnClickListener { sortUsers() }
        }
        return binding.root
    }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }

    private val observer: Observer<Resource<List<User>>> =
            Observer { users -> adapter.setUsers(users.data!!) }

    private fun sortUsers() {
        adapter.usersList = if (sortAscending)
            adapter.usersList.sortedBy { it.username }
        else
            adapter.usersList.sortedByDescending { it.username }
        sortAscending = !sortAscending
        binding.contentUser.rvUser.adapter = adapter
        binding.contentUser.rvUser.adapter!!.notifyDataSetChanged()
        Toast.makeText(this.activity, "Sort", Toast.LENGTH_SHORT).show()
    }
}