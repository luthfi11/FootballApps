package com.luthfialfarisi.footballapps.models

import com.google.gson.annotations.SerializedName

data class TeamResponse(@SerializedName("teams") val team: List<Team>)