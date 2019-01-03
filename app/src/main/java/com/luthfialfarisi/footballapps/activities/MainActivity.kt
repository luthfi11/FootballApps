package com.luthfialfarisi.footballapps.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
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

    public fun loadFragment(savedInstanceState: Bundle?, fragment: Fragment) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, fragment, fragment::class.java.simpleName)
                    .commit()
        }
    }

}
