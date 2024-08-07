package com.devapps.questionsoccer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devapps.questionsoccer.R
import com.devapps.questionsoccer.databinding.ItemLeagueBinding
import com.devapps.questionsoccer.items.LeagueResponseItem
import com.squareup.picasso.Picasso


class LeaguesAdapter (private var responseLeagues: List<LeagueResponseItem>, private val onLeagueClick: (LeagueResponseItem) -> Unit) : RecyclerView.Adapter<LeaguesAdapter.LeaguesViewHolder>(){

    var filteredLeagues: List<LeagueResponseItem> = responseLeagues
    class LeaguesViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = ItemLeagueBinding.bind(view)
        fun bind(responseLeagues: LeagueResponseItem?, onLeagueClick: (LeagueResponseItem) -> Unit){
            responseLeagues?.let {
                binding.tvLeagueName.text = it.league.name
                binding.tvLeagueType.text = it.league.type
                if (!it.league.logo.isNullOrEmpty()) {
                    Picasso.get().load(it.league.logo).into(binding.ivLeagueLogo)
                } else {

                }
                binding.LinearLayoutLeagues.setOnClickListener {
                    onLeagueClick(responseLeagues)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaguesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LeaguesViewHolder(layoutInflater.inflate(R.layout.item_league,parent,false))
    }

    override fun getItemCount(): Int {
        return filteredLeagues.size
    }

    override fun onBindViewHolder(holder: LeaguesViewHolder, position: Int) {
        val item = filteredLeagues[position]
        holder.bind(item, onLeagueClick)
    }

    fun filter(query: String){
        filteredLeagues = if (query.isEmpty()){
            responseLeagues
        }else{
            responseLeagues.filter { it.league.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}