package com.example.testapplication.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.api.PlaceHolderUser

class UserListAdapter: RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    lateinit var usersList: List<PlaceHolderUser>
    class UserViewHolder private constructor(var binding: ItemUserBinding) :
            RecyclerView.ViewHolder(
                    binding.root
            ) {
        fun bind(bindItem: String) {
            binding.userName = bindItem
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(binding)
            }
        }
    }

    fun getUserAtPosition(position: Int): User {
        return usersList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder.from(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = usersList.size

    fun setUsers(users: List<PlaceHolderUser>) {
        usersList = users
        notifyDataSetChanged()
    }

}