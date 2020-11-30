package com.example.testapplication.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.databinding.ItemPostBinding

class PostListAdapter : RecyclerView.Adapter<PostListAdapter.PostViewHolder>(), Filterable {
    var listOfPosts: List<PlaceHolderPost> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent, CommentListAdapter())
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = listOfPosts[position]
        with(holder) {
            bindPost(currentPost)
            adapter.setPosts(currentPost.comments)
            binding.contentComments.adapter = adapter
        }

    }

    override fun getItemCount() = listOfPosts.size

    fun setPosts(posts: List<PlaceHolderPost>) {
        listOfPosts = posts
        notifyDataSetChanged()
    }

    class PostViewHolder private constructor(
            var binding: ItemPostBinding,
            var adapter: CommentListAdapter) :
            RecyclerView.ViewHolder(binding.root) {

        fun bindPost(post: PlaceHolderPost) {
            binding.post = post
        }

        companion object {
            fun from(parent: ViewGroup, adapter: CommentListAdapter): PostViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPostBinding.inflate(layoutInflater, parent, false)
                return PostViewHolder(binding, adapter)
            }
        }
    }

    override fun getFilter(): Filter {
        return ItemFilter(listOfPosts)
    }

   inner class ItemFilter(private val postList: List<PlaceHolderPost>) : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {

            val filterString = constraint.toString()
            val results = FilterResults()

             val filteredList = postList.filter { post ->
                post.title.contains(filterString)
            }
            results.values = filteredList
            results.count = filteredList.size
            return results
        }
        @SuppressWarnings("unchecked")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            listOfPosts = results.values as List<PlaceHolderPost>
            notifyDataSetChanged()
        }
    }
}