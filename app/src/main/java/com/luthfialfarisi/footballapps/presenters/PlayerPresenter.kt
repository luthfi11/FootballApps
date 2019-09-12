package com.luthfialfarisi.footballapps.presenters

import com.google.gson.Gson
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.api.TheSportDBApi
import com.luthfialfarisi.footballapps.models.PlayerResponse
import com.luthfialfarisi.footballapps.utils.CoroutineContextProvider
import com.luthfialfarisi.footballapps.views.PlayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(private val view: PlayerView, private val apiRepository: ApiRepository, private val gson: Gson
                      , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerData(teamID: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayer(teamID)).await(), PlayerResponse::class.java)
            view.showPlayerList(data.player)
        }

    }
}