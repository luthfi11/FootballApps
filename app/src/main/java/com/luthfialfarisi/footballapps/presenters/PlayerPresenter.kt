package com.luthfialfarisi.footballapps.presenters

import com.google.gson.Gson
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.api.TheSportDBApi
import com.luthfialfarisi.footballapps.models.PlayerResponse
import com.luthfialfarisi.footballapps.utils.CoroutineContextProvider
import com.luthfialfarisi.footballapps.views.PlayerView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView, private val apiRepository: ApiRepository, private val gson: Gson
                      , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerData(teamID : String?) {

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayer(teamID)), PlayerResponse::class.java)
            }

            view.showPlayerList(data.await().player)
        }

    }
}