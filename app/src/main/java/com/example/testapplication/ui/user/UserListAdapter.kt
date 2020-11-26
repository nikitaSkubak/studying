package com.example.testapplication.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.databinding.ItemUserBinding

class UserListAdapter: RecyclerView.Adapter<UserListAdapter.UserViewHolder>(), Filterable {
    var usersList: List<PlaceHolderUser> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder.from(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = usersList[position]
        holder.bindUser(currentUser)
    }

    override fun getItemCount(): Int = usersList.size

    fun setUsers(users: List<PlaceHolderUser>) {
        usersList = users
        notifyDataSetChanged()
    }

    class UserViewHolder private constructor(var binding: ItemUserBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bindUser(user: PlaceHolderUser) {
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

    inner class ItemFilter(val listOfUsers: List<PlaceHolderUser>) : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {

            val filterString = constraint.toString()
            val results = FilterResults()
            val filteredListOfUsers: ArrayList<PlaceHolderUser> = arrayListOf()
            lateinit var filterableString: String

            listOfUsers.forEach { user ->
                filterableString = user.username
                if (filterableString == filterString)
                    filteredListOfUsers.add(user)
            }
            results.values = filteredListOfUsers
            results.count = filteredListOfUsers.size
            return results
        }
        @SuppressWarnings("unchecked")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            usersList = results.values as List<PlaceHolderUser>
            notifyDataSetChanged()
        }
    }

    override fun getFilter() = ItemFilter(usersList)
}