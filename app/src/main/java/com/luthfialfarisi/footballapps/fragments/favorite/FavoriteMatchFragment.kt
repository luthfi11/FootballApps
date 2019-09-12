package com.luthfialfarisi.footballapps.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.adapters.MatchAdapter
import com.luthfialfarisi.footballapps.models.Match
import com.luthfialfarisi.footballapps.utils.database
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteMatchFragment : Fragment() {

    private var match: MutableList<Match> = mutableListOf()
    private lateinit var adapter: MatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_match.layoutManager = LinearLayoutManager(activity)
        adapter = MatchAdapter(match)
        rv_match.adapter = adapter

        showFavorite()

        swipeRefreshFavMatch.onRefresh {
            match.clear()
            showFavorite()
        }
    }

    private fun showFavorite(){
        swipeRefreshFavMatch.isRefreshing = false
        context?.database?.use {
            val result = select(Match.TABLE_MATCH)
            val favorite = result.parseList(classParser<Match>())
            match.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}