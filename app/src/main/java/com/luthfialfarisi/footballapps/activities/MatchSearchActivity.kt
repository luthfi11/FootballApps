package com.luthfialfarisi.footballapps.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.adapters.MatchAdapter
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.models.League
import com.luthfialfarisi.footballapps.models.Match
import com.luthfialfarisi.footballapps.presenters.MatchPresenter
import com.luthfialfarisi.footballapps.views.MatchView
import kotlinx.android.synthetic.main.activity_match_search.*

class MatchSearchActivity : AppCompatActivity(), MatchView {

    private var match: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchAdapter

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showLeagueList(data: List<League>) {
    }

    override fun showMatchList(data: List<Match>) {
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        rv_match_search.layoutManager = LinearLayoutManager(this)
        adapter = MatchAdapter(match)
        rv_match_search.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        presenter = MatchPresenter(this, request, gson)

        search.requestFocus()
        search.isIconified = false
        matchSearch(search)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun matchSearch(searchView: android.widget.SearchView) {
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                presenter.getMatchSearch(query.toString())
                return true
            }
        })
    }
}
