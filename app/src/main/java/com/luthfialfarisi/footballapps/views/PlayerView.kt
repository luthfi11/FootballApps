package com.luthfialfarisi.footballapps.views

import com.luthfialfarisi.footballapps.models.Player

interface PlayerView {
    fun showPlayerList(data: List<Player>)
}