package com.luthfialfarisi.footballapps.presenters

import com.google.gson.Gson
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.api.TheSportDBApi
import com.luthfialfarisi.footballapps.models.LeagueResponse
import com.luthfialfarisi.footballapps.models.TeamResponse
import com.luthfialfarisi.footballapps.utils.CoroutineContextProvider
import com.luthfialfarisi.footballapps.views.TeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter(private val view: TeamView, private val apiRepository: ApiRepository, private val gson: Gson
                    , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagueList() {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeague()).await(), LeagueResponse::class.java)
            view.showLeagueList(data.leagues)
        }
    }

    fun getTeamList(league: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeam(league)).await(), TeamResponse::class.java)
            view.hideLoading()
            view.showTeamList(data.team)
        }
    }

    fun getTeamSearch(team: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamSearch(team)).await(), TeamResponse::class.java)
            view.showTeamList(data.team)
        }
    }
}