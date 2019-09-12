package com.luthfialfarisi.footballapps.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.R.id.*
import com.luthfialfarisi.footballapps.fragments.favorite.FavoriteFragmentManager
import com.luthfialfarisi.footballapps.fragments.match.MatchFragmentManager
import com.luthfialfarisi.footballapps.fragments.team.TeamFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                navigation_match -> {
                    loadFragment(savedInstanceState, MatchFragmentManager())
                }
                navigation_team -> {
                    loadFragment(savedInstanceState, TeamFragment())
                }
                navigation_favorite -> {
                    loadFragment(savedInstanceState, FavoriteFragmentManager())
                }
            }
            true
        }
        bottomNavigation.selectedItemId = navigation_match

    }

    fun loadFragment(savedInstanceState: Bundle?, fragment: Fragment) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, fragment, fragment::class.java.simpleName)
                    .commit()
        }
    }

}
