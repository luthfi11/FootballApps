package com.luthfialfarisi.footballapps.views

import com.luthfialfarisi.footballapps.models.League
import com.luthfialfarisi.footballapps.models.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<League>)
    fun showTeamList(data: List<Team>)
}