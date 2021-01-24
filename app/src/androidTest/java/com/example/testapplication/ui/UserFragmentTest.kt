package com.example.testapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.testapplication.R
import com.example.testapplication.dataBase.User
import com.example.testapplication.ui.user.UserFragment
import com.example.testapplication.ui.user.UserFragmentDirections
import com.example.testapplication.ui.user.UserListAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
class UserFragmentTest {
    private lateinit var userScenario: FragmentScenario<UserFragment>
    private lateinit var navController: NavController

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        userScenario = launchFragmentInContainer<UserFragment>()
        navController = Mockito.mock(NavController::class.java)

        userScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun clickUser_navigateToPostFragment() {

        onView(withText("Bret")).perform(click())
        verify(navController)
                .navigate(UserFragmentDirections.actionUserFragmentToPostFragment(1))
    }

    @Test
    fun searchUser_findUserInUserFragment() {

        onView(withId(R.id.tvSearchTitle))
                .perform(typeText("Bret"))
                .perform(closeSoftKeyboard())

        onView(withId(R.id.btnSearchTitle)).perform(click())

        onView(withId(R.id.tvUsername)).check(matches(withText("Bret")))

    }

    @Test
    fun clickUser_navigateToPostFragment_goBack() {

        onView(withText("Bret")).perform(click())
        verify(navController)
                .navigate(UserFragmentDirections.actionUserFragmentToPostFragment(1))
        pressBack()

        onView(withId(R.id.tvSearchTitle))
                .perform(typeText("Bret"))
                .perform(closeSoftKeyboard())

        onView(withId(R.id.btnSearchTitle)).perform(click())

        onView(withId(R.id.tvUsername)).check(matches(withText("Bret")))
    }

    @Test
    fun sortUserAscendant() {
        // Antonette is the first item of adapter, check is that true
        onView(withId(R.id.btnSort)).perform(click())
//        onView(withId(R.id.content_user))
//            .
//                RecyclerViewActions
//                    .actionOnItemAtPosition<UserListAdapter>(
//                        0,
//                        matches(withText("")))
//            .perform(
//                RecyclerViewActions
//                    .actionOnItemAtPosition<UserListAdapter
//                    .UserViewHolder>(0, click())
//            )
        Thread.sleep(3000)
        onView(withId(R.id.content_user))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<UserListAdapter
                    .UserViewHolder>(0, click())
            )
    }
}