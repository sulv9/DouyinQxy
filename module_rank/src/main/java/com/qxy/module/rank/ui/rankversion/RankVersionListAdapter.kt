package com.qxy.module.rank.ui.rankversion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qxy.lib.base.ext.DAY_MILLIS
import com.qxy.lib.base.ext.TIME_PATTERN_NO_HOUR
import com.qxy.lib.base.ext.getWeekOfDate
import com.qxy.lib.base.ext.toFormatDate
import com.qxy.module.rank.R
import com.qxy.module.rank.data.model.RankVersionItem
import com.qxy.module.rank.ext.transformDate
import java.util.*

class RankVersionListAdapter(
    private val onItemClickListener: (view: View) -> Unit,
    private val onItemViewInitListener: (itemView: View) -> Unit,
) : ListAdapter<RankVersionItem, RankVersionListAdapter.ViewHolder>(VersionSelectedDiffCallback) {
    class ViewHolder(
        itemView: View,
        val onItemClickListener: (view: View) -> Unit,
        onItemViewInitListener: (itemView: View) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView as TextView

        init {
            itemView.setOnClickListener { onItemClickListener(it) }
            onItemViewInitListener.invoke(itemView)
        }

        fun bind(pos: Int, rankVersion: RankVersionItem) {
            textView.text = if (pos == 0) {
                val datePair = getFirstAndLastDateOfCurrentWeek()
                "本周实时榜 ${datePair.first} - ${datePair.second}"
            }
            else
                "第${rankVersion.version}期 " +
                        "${rankVersion.startTime.transformDate} - " +
                        rankVersion.endTime.transformDate
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_rank_version, parent, false)
        return ViewHolder(view, onItemClickListener, onItemViewInitListener)
    }

    fun getSelectedVersionItem(position: Int): RankVersionItem? {
        return getItem(position)
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

private fun getFirstAndLastDateOfCurrentWeek(): Pair<String, String> {
    val currentDate = Date(System.currentTimeMillis())
    val currentWeek = getWeekOfDate(currentDate)
    val reduceNum = if (currentWeek == Calendar.SUNDAY) 6 else currentWeek - 2
    val firstFormatDate = (System.currentTimeMillis() - reduceNum * DAY_MILLIS)
        .toFormatDate(TIME_PATTERN_NO_HOUR)
        .transformDate
    val plusNum = 7 - reduceNum - 1
    val lastFormatDate = (System.currentTimeMillis() + plusNum * DAY_MILLIS)
        .toFormatDate(TIME_PATTERN_NO_HOUR)
        .transformDate
    return Pair(firstFormatDate, lastFormatDate)
}