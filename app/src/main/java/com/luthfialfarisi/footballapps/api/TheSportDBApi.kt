package com.luthfialfarisi.footballapps.api

import com.luthfialfarisi.footballapps.BuildConfig

object TheSportDBApi {
    fun getSchedule(matchItem : String?, leagueID: String?): String {
        return BuildConfig.BASE_URL + matchItem +"?id=" + leagueID
    }

    fun getTeam(league : String?): String {
        return BuildConfig.BASE_URL + "search_all_teams.php?l=" + league
    }

    fun getTeamLogo(teamID : String?): String {
        return BuildConfig.BASE_URL + "lookupteam.php?id=" + teamID
    }

    fun getPlayer(teamID: String?): String {
        return BuildConfig.BASE_URL + "lookup_all_players.php?id=" + teamID
    }

    fun getLeague(): String {
        return BuildConfig.BASE_URL + "search_all_leagues.php?s=Soccer"
    }

    fun getMatchSearch(match: String?): String {
        return BuildConfig.BASE_URL + "searchevents.php?e=" + match
    }

    fun getTeamSearch(team: String?): String {
        return BuildConfig.BASE_URL + "searchteams.php?t=" + team
    }
}