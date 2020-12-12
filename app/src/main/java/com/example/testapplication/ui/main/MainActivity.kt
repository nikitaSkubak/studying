package com.example.testapplication.ui.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.testapplication.R
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.testapplication.ui.user.UserFragmentDirections
import com.google.android.material.card.MaterialCardView
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    fun openPostsOfUser(v: View) {
        val id = Integer.parseInt((v as MaterialCardView).contentDescription.toString())
        val action = UserFragmentDirections.actionUserFragmentToPostFragment(id)
        v.findNavController().navigate(action)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}