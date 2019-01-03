package com.luthfialfarisi.footballapps.activities

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.fragments.match.LastMatchFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.activity.loadFragment(null, LastMatchFragment())
    }

    @Test
    fun testAppBehaviour() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.bottomNavigation)).check(matches(isDisplayed()))

        Thread.sleep(100)
        onView(withId(R.id.rv_schedule)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        Thread.sleep(3000)
        onView(withId(R.id.rv_schedule)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(13, click()))
        Thread.sleep(3000)
        onView(withId(R.id.add_to_favorite)).perform(click())
        Thread.sleep(1000)

        pressBack()

        onView(withId(R.id.navigation_team)).perform(click())
        Thread.sleep(100)
        onView(withId(R.id.rv_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        Thread.sleep(3000)
        onView(withId(R.id.rv_team)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(12, click()))
        Thread.sleep(3000)
        onView(withId(R.id.add_to_favorite)).perform(click())
        Thread.sleep(1000)

        pressBack()

        onView(withId(R.id.navigation_favorite)).perform(click())
        Thread.sleep(100)
        onView(withId(R.id.rv_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        Thread.sleep(3000)
        onView(withId(R.id.rv_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Thread.sleep(3000)
        onView(withId(R.id.add_to_favorite)).perform(click())
        Thread.sleep(1000)

        pressBack()

        Thread.sleep(500)

    }
}