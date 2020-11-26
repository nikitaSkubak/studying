package com.example.testapplication.ui.post

import android.os.Bundle
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
    private var sortAscending = true
    private val REPLY = "com.example.testapplication.USER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        setSupportActionBar(binding.appBar.toolbar)

        val id = Integer.parseInt(intent.getCharSequenceExtra(REPLY).toString())
        adapter = PostListAdapter()
        binding.contentPosts.rvPost.adapter = adapter

        postViewModel = injectViewModel(viewModelFactory)
        postViewModel.getPosts(id)
        postViewModel.posts.observe(this, observer)

        with(binding.appBar) {
            btnSearchTitle.setOnClickListener {
                adapter.filter.filter(tvSearchTitle.text.toString())
            }
        }
    }

    private val observer: Observer<List<PlaceHolderPost>> =
            Observer { posts -> adapter.setPosts(posts) }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.iSort -> {
                adapter.listOfPosts = if (sortAscending)
                    adapter.listOfPosts.sortedBy { it.title }
                else
                    adapter.listOfPosts.sortedByDescending { it.title }
                sortAscending = !sortAscending

                binding.contentPosts.rvPost.adapter = adapter
                binding.contentPosts.rvPost.adapter!!.notifyDataSetChanged()
                Toast.makeText(this, "Sort", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}