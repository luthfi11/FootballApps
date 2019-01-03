package com.luthfialfarisi.footballapps.presenters

import com.google.gson.Gson
import com.luthfialfarisi.footballapps.TestContextProvider
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.api.TheSportDBApi
import com.luthfialfarisi.footballapps.models.Team
import com.luthfialfarisi.footballapps.models.TeamResponse
import com.luthfialfarisi.footballapps.views.MatchDetailView
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest {

    @Mock
    private
    lateinit var view: MatchDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getHomeLogo() {
        val data: MutableList<Team> = mutableListOf()
        val response = TeamResponse(data)
        val id = "133604"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamLogo(id)),
                TeamResponse::class.java))
                .thenReturn(response)

        presenter.getHomeLogo(id)

        verify(view).showHomeLogo(data)
    }

    @Test
    fun getAwayLogo() {
        val data: MutableList<Team> = mutableListOf()
        val response = TeamResponse(data)
        val id = "133605"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamLogo(id)),
                TeamResponse::class.java))
                .thenReturn(response)

        presenter.getAwayLogo(id)

        verify(view).showAwayLogo(data)
    }
}