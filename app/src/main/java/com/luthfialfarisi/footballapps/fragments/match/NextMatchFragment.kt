package com.luthfialfarisi.footballapps.fragments.match

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.adapters.MatchAdapter
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.models.League
import com.luthfialfarisi.footballapps.models.Match
import com.luthfialfarisi.footballapps.presenters.MatchPresenter
import com.luthfialfarisi.footballapps.views.MatchView
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.support.v4.onRefresh

class NextMatchFragment : Fragment(), MatchView {

    private var match: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchAdapter
    private var league: MutableList<League> = mutableListOf()
    private val matchItem : String = "eventsnextleague.php"

    override fun showLeagueList(data: List<League>) {
        league.clear()
        league.addAll(data)
        spn_league.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, league)
    }

    override fun showLoading() {
        swipeRefreshMatch.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshMatch.isRefreshing = false
    }

    override fun showMatchList(data: List<Match>) {
        swipeRefreshMatch.isRefreshing = false
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_schedule.layoutManager = LinearLayoutManager(activity)
        adapter = MatchAdapter(match)
        rv_schedule.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        presenter = MatchPresenter(this, request, gson)
        presenter.getLeagueList()

        spn_league.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.getMatchList(matchItem, league[position].leagueID)
                Log.d("AAAAAAAAAA", "${league[position].leagueID} ${league[position].leagueName}")
                swipeRefreshMatch.onRefresh {
                    presenter.getMatchList(matchItem, league[position].leagueID)
                }
            }

        }
    }
}