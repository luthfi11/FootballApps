package com.luthfialfarisi.footballapps.activities

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.R.menu.favorite_menu
import com.luthfialfarisi.footballapps.fragments.team.PlayerFragment
import com.luthfialfarisi.footballapps.fragments.team.TeamOverviewFragment
import com.luthfialfarisi.footballapps.models.Team
import com.luthfialfarisi.footballapps.utils.database
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class TeamDetailActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private lateinit var team: Team
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        team = intent.getParcelableExtra("team")
        id = team.teamID+""

        getDetail(team)

        favoriteState()

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, team)

        viewpagerTeam.adapter = mSectionsPagerAdapter

        viewpagerTeam.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabsTeamDetail))
        tabsTeamDetail.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewpagerTeam))

    }

    private fun getDetail(team: Team) {
        tvTeamName.text = team.teamName
        tvYear.text = team.formedYear
        tvStadium.text = team.teamStadium

        Picasso.get().load(team.teamBadge).into(imgClubDetail)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, val team: Team?) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val fragment : Fragment
            when (position) {
                0 -> {
                    fragment = TeamOverviewFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("team", team)
                    fragment.arguments = bundle
                    return fragment
                }
                1 -> {
                    fragment = PlayerFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("team", team)
                    fragment.arguments = bundle
                    return fragment
                }
            }
            return TeamOverviewFragment()
        }

        override fun getCount(): Int {
            return 2
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(favorite_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
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
                insert(Team.TABLE_TEAM,
                        Team.TEAM_ID to team.teamID,
                        Team.TEAM_NAME to team.teamName,
                        Team.FORMED_YEAR to team.formedYear,
                        Team.STADIUM to team.teamStadium,
                        Team.DESCIPTION to team.teamDesc,
                        Team.BADGE to team.teamBadge)
            }
            snackbar(tabsTeamDetail, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(tabsTeamDetail, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Team.TABLE_TEAM, "(TEAM_ID = {teamID})", "teamID" to id)
                Log.d("ID EVENT", id)
            }
            snackbar(tabsTeamDetail, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(tabsTeamDetail, e.localizedMessage).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(Team.TABLE_TEAM).whereArgs("(TEAM_ID = {teamID})", "teamID" to id)
            val favorite = result.parseList(classParser<Team>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}
