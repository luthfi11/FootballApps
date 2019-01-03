package com.luthfialfarisi.footballapps.presenters

import com.google.gson.Gson
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.api.TheSportDBApi
import com.luthfialfarisi.footballapps.models.TeamResponse
import com.luthfialfarisi.footballapps.utils.CoroutineContextProvider
import com.luthfialfarisi.footballapps.views.MatchDetailView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchDetailPresenter(private val view: MatchDetailView, private val apiRepository: ApiRepository, private val gson: Gson
                           , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getHomeLogo(teamID : String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamLogo(teamID)), TeamResponse::class.java)
            }
            view.showHomeLogo(data.await().team)
        }
    }

    fun getAwayLogo(teamID : String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamLogo(teamID)), TeamResponse::class.java)
            }
            view.showAwayLogo(data.await().team)
        }
    }
}