package com.qxy.module.rank.ui

import androidx.recyclerview.widget.RecyclerView
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.databinding.ItemRecyclerRankBinding

class RankRecyclerAdapter {
    class RankViewHolder(
        private val binding: ItemRecyclerRankBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rankItem: RankItem) {
//            binding.rankItemIvPoster.setImageResource(rankItem.)
        }
    }
}