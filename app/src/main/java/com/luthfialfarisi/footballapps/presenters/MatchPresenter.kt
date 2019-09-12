package com.luthfialfarisi.footballapps.presenters

import com.google.gson.Gson
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.api.TheSportDBApi
import com.luthfialfarisi.footballapps.models.LeagueResponse
import com.luthfialfarisi.footballapps.models.MatchResponse
import com.luthfialfarisi.footballapps.models.MatchSearchResponse
import com.luthfialfarisi.footballapps.utils.CoroutineContextProvider
import com.luthfialfarisi.footballapps.views.MatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter(private val view: MatchView, private val apiRepository: ApiRepository, private val gson: Gson
                     , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagueList() {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLeague()).await(), LeagueResponse::class.java)
            view.showLeagueList(data.leagues)
        }
    }

    fun getMatchList(matchItem: String?, leagueID: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSchedule(matchItem, leagueID)).await(), MatchResponse::class.java)
            view.hideLoading()

            if (data.match != null)
                view.showMatchList(data.match)
        }
    }

    fun getMatchSearch(keyword: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getMatchSearch(keyword)).await(), MatchSearchResponse::class.java)
            view.showMatchList(data.match)
        }
    }
}