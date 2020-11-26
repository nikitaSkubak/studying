package com.example.testapplication.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.databinding.ItemPostBinding

class PostListAdapter: RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {
    var listOfPosts: List<PlaceHolderPost> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        //здесь создать новый адаптер, а лучше еще чекнуть в интернете примеры
       return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = listOfPosts[position]
        holder.bindPost(currentPost)
    }

    override fun getItemCount() = listOfPosts.size

    fun setPosts(posts: List<PlaceHolderPost>){
        listOfPosts = posts
        notifyDataSetChanged()
    }

    class PostViewHolder private constructor(var binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindPost(post: PlaceHolderPost) {
            binding.post = post
        }

        companion object {
            fun from(parent: ViewGroup): PostViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPostBinding.inflate(layoutInflater, parent, false)
                return PostViewHolder(binding)
            }
        }
    }
}