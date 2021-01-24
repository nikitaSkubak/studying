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
import com.example.testapplication.util.CustomKt
import com.example.testapplication.vo.Resource
import com.example.testapplication.vo.Status
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.seacrh_layout.*
import javax.inject.Inject


private const val getUserId = "userId"

class PostFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var postViewModel: PostViewModel
    private lateinit var adapter: PostListAdapter
    private var userId: Int = 1
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PostListAdapter()
        binding.contentPosts.rvPost.adapter = adapter

        postViewModel = injectViewModel(viewModelFactory)
        postViewModel.getPosts(userId)
        postViewModel.postsData.observe(viewLifecycleOwner, postsObserver)

        CustomKt.forThem(btnSearchTitle, btnSort) {
            setOnClickListener(clickListener)
        }
    }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }

    private val postsObserver: Observer<Resource<List<PlaceHolderPost>>> =
            Observer {
                when (it.status) {
                    Status.SUCCESS -> adapter.setPosts(it.data!!)
                    Status.LOADING -> Toast
                            .makeText(this.context, "loading", Toast.LENGTH_SHORT)
                            .show()
                    Status.ERROR -> Toast
                            .makeText(this.context, it.message, Toast.LENGTH_SHORT)
                            .show()
                }
            }

    private fun sortPosts() {
        with(adapter) {
            listOfPosts = if (sortAscending)
                listOfPosts.sortedBy { it.title }
            else
                listOfPosts.sortedByDescending { it.title }
            sortAscending = !sortAscending

            binding.contentPosts.rvPost.adapter = this
            binding.contentPosts.rvPost.adapter!!.notifyDataSetChanged()
        }
        Toast.makeText(this.activity, "Sort", Toast.LENGTH_SHORT).show()

    }

    private val clickListener = View.OnClickListener {
        when (it.id) {
            btnSearchTitle.id -> { adapter.filter.filter(tvSearchTitle.text.toString()) }
            btnSort.id -> { sortPosts() }
        }
    }


}