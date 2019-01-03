package com.luthfialfarisi.footballapps.models

import com.google.gson.annotations.SerializedName

data class LeagueResponse(@SerializedName("countrys")
                          val leagues: MutableList<League>)
