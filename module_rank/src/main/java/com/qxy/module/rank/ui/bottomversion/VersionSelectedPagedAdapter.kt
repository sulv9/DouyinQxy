package com.qxy.module.rank.ui.bottomversion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qxy.module.rank.R
import com.qxy.module.rank.data.model.RankVersionItem

class VersionSelectedPagedAdapter(
    private val onItemClickListener: (view: View) -> Unit,
) : ListAdapter<RankVersionItem, VersionSelectedPagedAdapter.ViewHolder>(
    VersionSelectedDiffCallback
) {
    class ViewHolder(
        itemView: View,
        val onItemClickListener: (view: View) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView as TextView

        init {
            itemView.setOnClickListener { onItemClickListener(it) }
        }

        fun bind(rankVersion: RankVersionItem) {
            textView.text = "${rankVersion.version}"
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_rank_bottom, parent, false)
        return ViewHolder(view, onItemClickListener)
    }
}

object VersionSelectedDiffCallback : DiffUtil.ItemCallback<RankVersionItem>() {
    override fun areItemsTheSame(oldItem: RankVersionItem, newItem: RankVersionItem): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: RankVersionItem, newItem: RankVersionItem): Boolean {
        return oldItem == newItem
    }

}