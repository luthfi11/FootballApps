package com.luthfialfarisi.footballapps.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(
        @SerializedName("idLeague")
        var leagueID: String? = "",

        @SerializedName("strLeague")
        var leagueName: String? = ""
) : Parcelable {
        override fun toString(): String {
                return leagueName.orEmpty()
        }
}
