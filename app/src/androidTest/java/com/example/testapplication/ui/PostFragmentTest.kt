package com.example.testapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.testapplication.R
import com.example.testapplication.ui.post.PostFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
class PostFragmentTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var postScenario: FragmentScenario<PostFragment>
    private lateinit var navController: NavController

    @Before
    fun setup() {
        postScenario = launchFragmentInContainer<PostFragment>()
        navController = Mockito.mock(NavController::class.java)

        postScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun searchPostInPostFragment() {
        val title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
        Thread.sleep(5000)
        onView(ViewMatchers.withId(R.id.tvSearchTitle))
            .perform(ViewActions.typeText("repellat"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(ViewMatchers.withId(R.id.btnSearchTitle)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.withText(title)))
    }
}