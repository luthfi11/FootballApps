package com.luthfialfarisi.footballapps.models

import com.google.gson.annotations.SerializedName

data class MatchResponse(@SerializedName("events") val match: List<Match>)

data class MatchSearchResponse(@SerializedName("event") val match: List<Match>)