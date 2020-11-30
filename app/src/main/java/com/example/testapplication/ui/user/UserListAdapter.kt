package com.example.testapplication.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.dataBase.User
import com.example.testapplication.databinding.ItemUserBinding

class UserListAdapter: RecyclerView.Adapter<UserListAdapter.UserViewHolder>(), Filterable {
    var usersList: List<User> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder.from(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = usersList[position]
        holder.bindUser(currentUser)
    }

    override fun getItemCount(): Int = usersList.size

    fun setUsers(users: List<User>) {
        usersList = users
        notifyDataSetChanged()
    }

    class UserViewHolder private constructor(var binding: ItemUserBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bindUser(user: User) {
            binding.user = user
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(binding)
            }
        }
    }

    inner class ItemFilter(private val listOfUsers: List<User>) : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {

            val filterString = constraint.toString()
            val results = FilterResults()

            val filteredList = listOfUsers.filter { user ->
               user.username == filterString
            }
            results.values = filteredList
            results.count = filteredList.size
            return results
        }
        @SuppressWarnings("unchecked")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            usersList = results.values as List<User>
            notifyDataSetChanged()
        }
    }

    override fun getFilter() = ItemFilter(usersList)
}