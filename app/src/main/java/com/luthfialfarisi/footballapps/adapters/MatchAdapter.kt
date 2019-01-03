package com.luthfialfarisi.footballapps.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.luthfialfarisi.footballapps.R
import com.luthfialfarisi.footballapps.activities.MatchDetailActivity
import com.luthfialfarisi.footballapps.models.Match
import kotlinx.android.synthetic.main.item_schedule.view.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter (private val match: MutableList<Match>)
    : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false))

    override fun getItemCount(): Int = match.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(match[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(match: Match) {
            val format = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            val sdf = SimpleDateFormat("E, dd MMMM yyyy", Locale.getDefault())
            if(match.matchDate != null){
                val date : String = sdf.format(format.parse(match.matchDate))
                itemView.tvDate.text = date
            }
            itemView.tvHomeTeam.text = match.homeTeam
            itemView.tvAwayTeam.text = match.awayTeam
            if (match.homeTeamScore == null) {
                itemView.tvScore.text = "Vs"
            } else {
                itemView.tvScore.text = "${match.homeTeamScore} Vs ${match.awayTeamScore}"
            }

            itemView.setOnClickListener {
                itemView.context.startActivity<MatchDetailActivity>("match" to match)
            }
        }
    }
}