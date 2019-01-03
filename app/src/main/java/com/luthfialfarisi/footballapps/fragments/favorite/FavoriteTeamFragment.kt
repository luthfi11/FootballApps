package com.luthfialfarisi.footballapps.fragments.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.adapters.TeamAdapter
import com.luthfialfarisi.footballapps.models.Team
import com.luthfialfarisi.footballapps.utils.database
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteTeamFragment : Fragment() {

    private var team: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_team_fav.layoutManager = GridLayoutManager(activity, 2)
        adapter = TeamAdapter(team)
        rv_team_fav.adapter = adapter

        showFavorite()

        swipeRefreshFavTeam.onRefresh {
            team.clear()
            showFavorite()
        }
    }

    private fun showFavorite(){
        swipeRefreshFavTeam.isRefreshing = false
        context?.database?.use {
            val result = select(Team.TABLE_TEAM)
            val favorite = result.parseList(classParser<Team>())
            team.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}