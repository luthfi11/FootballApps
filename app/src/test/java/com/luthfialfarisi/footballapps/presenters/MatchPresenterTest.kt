package com.luthfialfarisi.footballapps.presenters

import com.google.gson.Gson
import com.luthfialfarisi.footballapps.TestContextProvider
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.api.TheSportDBApi
import com.luthfialfarisi.footballapps.models.Match
import com.luthfialfarisi.footballapps.models.MatchResponse
import com.luthfialfarisi.footballapps.views.MatchView
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchList() {
        val data: MutableList<Match> = mutableListOf()
        val response = MatchResponse(data)
        val key = "eventspastleague.php"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getSchedule(key)),
                MatchResponse::class.java))
                .thenReturn(response)

        presenter.getMatchList(key)

        verify(view).showLoading()
        verify(view).showMatchList(data)
        verify(view).hideLoading()
    }

}