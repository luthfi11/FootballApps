package com.luthfialfarisi.footballapps.views

import com.luthfialfarisi.footballapps.models.Team

interface MatchDetailView {
    fun showHomeLogo(team: List<Team>)
    fun showAwayLogo(team: List<Team>)
}