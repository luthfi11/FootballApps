package com.luthfialfarisi.footballapps.views

import com.luthfialfarisi.footballapps.models.League
import com.luthfialfarisi.footballapps.models.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<League>)
    fun showMatchList(data: List<Match>)
}