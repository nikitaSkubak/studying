package com.example.testapplication.ui.post

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testapplication.R
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.databinding.ActivityPostBinding
import com.example.testapplication.main.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class PostActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var postViewModel: PostViewModel
    private lateinit var adapter: PostListAdapter
    private lateinit var binding: ActivityPostBinding
    private val REPLY = "com.example.testapplication.USER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        val id = Integer.parseInt(intent.getCharSequenceExtra(REPLY).toString())
        adapter = PostListAdapter()
        binding.contentPosts.rvPost.adapter = adapter

        postViewModel = injectViewModel(viewModelFactory)
        postViewModel.getPosts(id)

        postViewModel.posts.observe(this, observer)

    }

    private val observer: Observer<List<PlaceHolderPost>> =
        Observer { posts -> adapter.setPosts(posts) }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }
}