package com.qxy.module.personal.ui.fanfollow.fan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qxy.lib.base.image.GlideApp
import com.qxy.lib.common.R
import com.qxy.module.personal.data.model.PersonalFanFollowingItem
import com.qxy.module.personal.databinding.ItemRecyclerPersonalFanBinding

class PersonalFanRecyclerAdapter :
    ListAdapter<PersonalFanFollowingItem, PersonalFanRecyclerAdapter.ViewHolder>(
        PersonalFanFollowingDiffCallback
    ) {
    class ViewHolder(
        private val binding: ItemRecyclerPersonalFanBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(personalFanFollowingItem: PersonalFanFollowingItem) {
            with(binding.personalFanItemIvAvatar) {
                GlideApp.with(context)
                    .load(personalFanFollowingItem.avatar)
                    .thumbnail(
                        GlideApp.with(context)
                            .load(R.drawable.common_ic_loading)
                    ).into(this)
            }
            binding.personalFanItemTvName.text = personalFanFollowingItem.nickName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerPersonalFanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object PersonalFanFollowingDiffCallback : DiffUtil.ItemCallback<PersonalFanFollowingItem>() {
    override fun areItemsTheSame(
        oldItem: PersonalFanFollowingItem,
        newItem: PersonalFanFollowingItem
    ): Boolean {
        return oldItem.openId == newItem.openId
    }

    override fun areContentsTheSame(
        oldItem: PersonalFanFollowingItem,
        newItem: PersonalFanFollowingItem
    ): Boolean {
        return oldItem == newItem
    }
}