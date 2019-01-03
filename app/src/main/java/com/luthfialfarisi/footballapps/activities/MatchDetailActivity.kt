package com.luthfialfarisi.footballapps.activities

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.R.drawable.ic_add_to_favorites
import com.luthfialfarisi.footballapps.R.drawable.ic_added_to_favorites
import com.luthfialfarisi.footballapps.R.id.add_to_favorite
import com.luthfialfarisi.footballapps.R.menu.favorite_menu
import com.luthfialfarisi.footballapps.api.ApiRepository
import com.luthfialfarisi.footballapps.models.Match
import com.luthfialfarisi.footballapps.models.Team
import com.luthfialfarisi.footballapps.presenters.MatchDetailPresenter
import com.luthfialfarisi.footballapps.utils.database
import com.luthfialfarisi.footballapps.views.MatchDetailView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.design.snackbar
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat
import java.util.*

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {

    override fun showHomeLogo(team: List<Team>) {
        Picasso.get().load(team[0].teamBadge).into(imgHomeTeam)
    }

    override fun showAwayLogo(team: List<Team>) {
        Picasso.get().load(team[0].teamBadge).into(imgAwayTeam)
    }

    private lateinit var match: Match
    private lateinit var presenter: MatchDetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        match = intent.getParcelableExtra("match")
        id = match.eventId+""

        getDetail(match)

        favoriteState()

        val request = ApiRepository()
        val gson = Gson()

        presenter = MatchDetailPresenter(this, request, gson)
        presenter.run {
             getHomeLogo(match.homeTeamID)
             getAwayLogo(match.awayTeamID)
        }

    }

    private fun getDetail(match: Match) {

        if(match.matchDate != null) {
            val format = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            val sdf = SimpleDateFormat("E, dd MMMM yyyy", Locale.getDefault())
            val date: String = sdf.format(format.parse(match.matchDate))

            tvDateDetail.text = date
        }
        tvHomeTeamDetail.text = match.homeTeam
        tvAwayTeamDetail.text = match.awayTeam
        tvHomeGoal.text = match.homeScorer
        tvAwayGoal.text = match.awayScorer
        tvHomeScore.text = match.homeTeamScore
        tvAwayScore.text = match.awayTeamScore
        tvHomeShot.text = match.homeShot
        tvAwayShot.text = match.awayShot
        tvHomeGK.text = match.homeGK
        tvAwayGK.text = match.awayGK
        tvHomeDF.text = match.homeDF
        tvAwayDF.text = match.awayDF
        tvHomeMF.text = match.homeMF
        tvAwayMF.text = match.awayMF
        tvHomeFW.text = match.homeFW
        tvAwayFW.text = match.awayFW
        tvHomeSub.text = match.homeSubs
        tvAwaySub.text = match.awaySubs

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(favorite_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Match.TABLE_MATCH,
                        Match.EVENT_ID to match.eventId,
                        Match.MATCH_DATE to match.matchDate,
                        Match.HOME_TEAM to match.homeTeam,
                        Match.AWAY_TEAM to match.awayTeam,
                        Match.HOME_SCORE to match.homeTeamScore,
                        Match.AWAY_SCORE to match.awayTeamScore,
                        Match.HOME_SCORER to match.homeScorer,
                        Match.AWAY_SCORER to match.awayScorer,
                        Match.HOME_SHOT to match.homeShot,
                        Match.AWAY_SHOT to match.awayShot,
                        Match.HOME_GK to match.homeGK,
                        Match.AWAY_GK to match.awayGK,
                        Match.HOME_DF to match.homeDF,
                        Match.AWAY_DF to match.awayDF,
                        Match.HOME_MF to match.homeMF,
                        Match.AWAY_MF to match.awayMF,
                        Match.HOME_FW to match.homeFW,
                        Match.AWAY_FW to match.awayFW,
                        Match.HOME_SUB to match.homeSubs,
                        Match.AWAY_SUB to match.awaySubs,
                        Match.HOME_ID to match.homeTeamID,
                        Match.AWAY_ID to match.awayTeamID)
            }
            snackbar(tvSub, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(tvSub, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Match.TABLE_MATCH, "(EVENT_ID = {eventId})", "eventId" to id)
                Log.d("ID EVENT", id)
            }
            snackbar(tvSub, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(tvSub, e.localizedMessage).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(Match.TABLE_MATCH).whereArgs("(EVENT_ID = {eventId})", "eventId" to id)
            val favorite = result.parseList(classParser<Match>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}
