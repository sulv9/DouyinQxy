package com.qxy.module.personal.ui.personal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qxy.module.personal.R
import com.qxy.module.personal.data.model.PersonalVideo
import com.qxy.module.personal.databinding.ItemPersonalVideoBinding

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/21 021 21:10
 */
class PersonalVideoListAdapter :
    ListAdapter<PersonalVideo.ListElement, PersonalVideoListAdapter.ViewHolder>(COMPARATOR) {
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<PersonalVideo.ListElement>() {
            override fun areContentsTheSame(
                oldItem: PersonalVideo.ListElement,
                newItem: PersonalVideo.ListElement
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: PersonalVideo.ListElement,
                newItem: PersonalVideo.ListElement
            ): Boolean {
                return oldItem.videoID == newItem.videoID
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding: ItemPersonalVideoBinding

        init {
            binding = ItemPersonalVideoBinding.bind(view)
            binding.tvLike.isGone = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_personal_video, parent, false)
        val viewHolder = ViewHolder(view)
        // click event here.
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            Glide.with(holder.binding.root).load(item.cover).into(holder.binding.ivVideo)
            holder.binding.tvTop.isVisible = item.isTop
        }
    }
}