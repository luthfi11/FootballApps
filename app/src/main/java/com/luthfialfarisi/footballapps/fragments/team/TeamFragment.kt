package com.luthfialfarisi.footballapps.fragments.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.google.gson.Gson
import com.luthfialfarisi.footballapps.BuildConfig
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.R.menu.favorite_menu
import com.luthfialfarisi.footballapps.R.menu.search_menu
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.models.Team
import com.luthfialfarisi.footballapps.presenters.TeamPresenter
import com.luthfialfarisi.footballapps.views.TeamView
import com.luthfialfarisi.footballapps.adapters.TeamAdapter
import com.luthfialfarisi.footballapps.models.League
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.support.v4.onRefresh

class TeamFragment : Fragment(), TeamView {
    private var team: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private var league: MutableList<League> = mutableListOf()
    private var leagues: String = ""

    override fun showLeagueList(data: List<League>) {
        league.clear()
        league.addAll(data)
        spn_league_team.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, league)
    }

    override fun showLoading() {
        swipeRefreshTeam.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshTeam.isRefreshing = false
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefreshTeam.isRefreshing = false
        team.clear()
        team.addAll(data)
        adapter.notifyDataSetChanged()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        rv_team.layoutManager = GridLayoutManager(activity, 2)
        adapter = TeamAdapter(team)
        rv_team.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        presenter = TeamPresenter(this, request, gson)
        presenter.getLeagueList()

        spn_league_team.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagues = spn_league_team.selectedItem.toString().replace(" ", "%20")

                presenter.getTeamList(leagues)

                swipeRefreshTeam.onRefresh {
                    presenter.getTeamList(leagues)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater?.inflate(search_menu, menu)

        val searchMenu = menu?.findItem(R.id.teamSearch)
        val searchView = searchMenu?.actionView as android.support.v7.widget.SearchView

        teamSearch(searchView)
    }

    private fun teamSearch(searchView: android.support.v7.widget.SearchView) {
        searchView.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query.toString().isNotEmpty()) {
                    spn_league_team.visibility = View.GONE
                    presenter.getTeamSearch(query.toString())
                } else {
                    spn_league_team.visibility = View.VISIBLE
                    presenter.getTeamList(spn_league_team.selectedItem.toString().replace(" ", "%20"))
                }
                return true
            }
        })
    }
}