package com.luthfialfarisi.footballapps.presenters

import com.google.gson.Gson
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.api.TheSportDBApi
import com.luthfialfarisi.footballapps.models.LeagueResponse
import com.luthfialfarisi.footballapps.models.MatchResponse
import com.luthfialfarisi.footballapps.models.MatchSearchResponse
import com.luthfialfarisi.footballapps.utils.CoroutineContextProvider
import com.luthfialfarisi.footballapps.views.MatchView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(private val view: MatchView, private val apiRepository: ApiRepository, private val gson: Gson
                     , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagueList() {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeague()), LeagueResponse::class.java)
            }
            view.showLeagueList(data.await().leagues)
        }
    }

    fun getMatchList(matchItem : String?, leagueID: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSchedule(matchItem, leagueID)), MatchResponse::class.java)
            }
            view.hideLoading()
            view.showMatchList(data.await().match)
        }
    }

    fun getMatchSearch(keyword : String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getMatchSearch(keyword)), MatchSearchResponse::class.java)
            }
            view.showMatchList(data.await().match)
        }

    }
}