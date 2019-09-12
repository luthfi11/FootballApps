package com.luthfialfarisi.footballapps.fragments.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.adapters.PlayerAdapter
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.models.Player
import com.luthfialfarisi.footballapps.models.Team
import com.luthfialfarisi.footballapps.presenters.PlayerPresenter
import com.luthfialfarisi.footballapps.views.PlayerView
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment: Fragment(), PlayerView {

    private var player: MutableList<Player> = mutableListOf()

    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter
    private var teamID : Team? = null

    override fun showPlayerList(data: List<Player>) {
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_player.layoutManager = GridLayoutManager(activity,2)
        adapter = PlayerAdapter(player)
        rv_player.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        val bundle = arguments
        teamID = bundle?.getParcelable("team")

        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayerData(teamID?.teamID)

    }
}