package com.luthfialfarisi.footballapps.presenters

import com.google.gson.Gson
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.api.TheSportDBApi
import com.luthfialfarisi.footballapps.models.LeagueResponse
import com.luthfialfarisi.footballapps.models.TeamResponse
import com.luthfialfarisi.footballapps.utils.CoroutineContextProvider
import com.luthfialfarisi.footballapps.views.TeamView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(private val view: TeamView, private val apiRepository: ApiRepository, private val gson: Gson
                    , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagueList() {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeague()), LeagueResponse::class.java)
            }
            view.showLeagueList(data.await().leagues)
        }
    }

    fun getTeamList(league : String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeam(league)), TeamResponse::class.java)
            }
            view.hideLoading()
            view.showTeamList(data.await().team)
        }
    }

    fun getTeamSearch(team : String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamSearch(team)), TeamResponse::class.java)
            }
            view.showTeamList(data.await().team)
        }
    }
}