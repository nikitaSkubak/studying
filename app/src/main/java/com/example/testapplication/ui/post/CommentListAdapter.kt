package com.example.testapplication.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.api.PlaceHolderComment
import com.example.testapplication.databinding.ItemCommentBinding

class CommentListAdapter: RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {
    var listOfComments: List<PlaceHolderComment> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CommentViewHolder.from(parent)

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindComment(listOfComments[position])
    }

    override fun getItemCount() = listOfComments.size

    fun setPosts(comments: List<PlaceHolderComment>){
        listOfComments = comments
        notifyDataSetChanged()
    }

    class CommentViewHolder private constructor(var binding: ItemCommentBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bindComment(comment: PlaceHolderComment) {
            binding.comment = comment
        }

        companion object {
            fun from(parent: ViewGroup): CommentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCommentBinding.inflate(layoutInflater, parent, false)
                return CommentViewHolder(binding)
            }
        }
    }


}