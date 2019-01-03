package com.luthfialfarisi.footballapps.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.luthfialfarisi.footballapps.models.Match
import com.luthfialfarisi.footballapps.models.Team
import org.jetbrains.anko.db.*

class DatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Football.db", null, 1) {
    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(context.applicationContext)
            }
            return instance as DatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Match.TABLE_MATCH, true,
                Match.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Match.EVENT_ID to TEXT + UNIQUE,
                Match.MATCH_DATE to TEXT,
                Match.HOME_TEAM to TEXT,
                Match.AWAY_TEAM to TEXT,
                Match.HOME_SCORE to TEXT,
                Match.AWAY_SCORE to TEXT,
                Match.HOME_SCORER to TEXT,
                Match.AWAY_SCORER to TEXT,
                Match.HOME_SHOT to TEXT,
                Match.AWAY_SHOT to TEXT,
                Match.HOME_GK to TEXT,
                Match.AWAY_GK to TEXT,
                Match.HOME_DF to TEXT,
                Match.AWAY_DF to TEXT,
                Match.HOME_MF to TEXT,
                Match.AWAY_MF to TEXT,
                Match.HOME_FW to TEXT,
                Match.AWAY_FW to TEXT,
                Match.HOME_SUB to TEXT,
                Match.AWAY_SUB to TEXT,
                Match.HOME_ID to TEXT,
                Match.AWAY_ID to TEXT)

        db.createTable(Team.TABLE_TEAM, true,
                Team.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Team.TEAM_ID to TEXT + UNIQUE,
                Team.TEAM_NAME to TEXT,
                Team.FORMED_YEAR to TEXT,
                Team.STADIUM to TEXT,
                Team.DESCIPTION to TEXT,
                Team.BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Match.TABLE_MATCH, true)
        db.dropTable(Team.TABLE_TEAM, true)
    }
}

val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)