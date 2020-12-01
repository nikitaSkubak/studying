package com.example.testapplication.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.databinding.FragmentPostBinding
import com.example.testapplication.main.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.seacrh_layout.view.*
import javax.inject.Inject


private const val getUserId = "userId"

class PostFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var postViewModel: PostViewModel
    private lateinit var adapter: PostListAdapter
    private var userId: Int = 0
    lateinit var binding: FragmentPostBinding
    private var sortAscending = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getInt(getUserId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(layoutInflater, container, false)
        adapter = PostListAdapter()
        binding.contentPosts.rvPost.adapter = adapter

        postViewModel = injectViewModel(viewModelFactory)
        postViewModel.getPosts(userId)
        postViewModel.posts.observe(viewLifecycleOwner, observer)

        with(binding.lSearch) {
            btnSearchTitle.setOnClickListener {
                adapter.filter.filter(tvSearchTitle.text.toString())
            }
            btnSort.setOnClickListener { sortPosts() }
        }

        return binding.root
    }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }

    private val observer: Observer<List<PlaceHolderPost>> =
        Observer { posts -> adapter.setPosts(posts) }

    private fun sortPosts() {
        adapter.listOfPosts = if (sortAscending)
            adapter.listOfPosts.sortedBy { it.title }
        else
            adapter.listOfPosts.sortedByDescending { it.title }
        sortAscending = !sortAscending

        binding.contentPosts.rvPost.adapter = adapter
        binding.contentPosts.rvPost.adapter!!.notifyDataSetChanged()
        Toast.makeText(this.activity, "Sort", Toast.LENGTH_SHORT).show()
    }
}