package com.luthfialfarisi.footballapps.presenters

import com.google.gson.Gson
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.api.TheSportDBApi
import com.luthfialfarisi.footballapps.models.TeamResponse
import com.luthfialfarisi.footballapps.utils.CoroutineContextProvider
import com.luthfialfarisi.footballapps.views.MatchDetailView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter(private val view: MatchDetailView, private val apiRepository: ApiRepository, private val gson: Gson
                           , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getHomeLogo(teamID: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamLogo(teamID)).await(), TeamResponse::class.java)
            view.showHomeLogo(data.team)
        }
    }

    fun getAwayLogo(teamID: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamLogo(teamID)).await(), TeamResponse::class.java)
            view.showAwayLogo(data.team)
        }
    }
}