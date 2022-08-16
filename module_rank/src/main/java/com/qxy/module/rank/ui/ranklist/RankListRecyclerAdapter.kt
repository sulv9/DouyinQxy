package com.qxy.module.rank.ui.ranklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.qxy.lib.base.ext.dp
import com.qxy.lib.base.image.GlideApp
import com.qxy.lib.common.R
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.databinding.ItemRecyclerRankBinding

class RankRecyclerAdapter :
    ListAdapter<RankItem, RankRecyclerAdapter.RankViewHolder>(RankListDiffCallback()) {

    class RankViewHolder(
        private val binding: ItemRecyclerRankBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.rankItemTvBuyTicket.setOnClickListener {
                // TODO 跳转到购票界面
            }
        }

        fun bind(rankItem: RankItem) {
            // 绑定图片
            with(binding.rankItemIvPoster) {
                GlideApp.with(context)
                    .load(rankItem.poster)
                    .thumbnail(
                        GlideApp.with(context)
                            .load(R.drawable.common_ic_loading)
                    )
                    .transform(CenterCrop(), RoundedCorners(2.dp))
                    .into(this)
            }
            binding.rankItemTvName.text = rankItem.name
            binding.rankItemTvTags.text = rankItem.tags?.joinToString(" / ") ?: ""
            binding.rankItemTvReleaseDate.text = rankItem.releaseDate + " 上映"
            binding.rankItemTvHot.text = "🔥 ${rankItem.hot / 1e4}万"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        return RankViewHolder(
            ItemRecyclerRankBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class RankListDiffCallback : DiffUtil.ItemCallback<RankItem>() {
    override fun areItemsTheSame(oldItem: RankItem, newItem: RankItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RankItem, newItem: RankItem): Boolean {
        return oldItem == newItem
    }

}