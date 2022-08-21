package com.qxy.module.personal.ui.fanfollow.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qxy.lib.base.image.GlideApp
import com.qxy.lib.common.R
import com.qxy.module.personal.data.model.PersonalFanFollowingItem
import com.qxy.module.personal.databinding.ItemRecyclerPersonalFollowBinding
import com.qxy.module.personal.ui.fanfollow.fan.PersonalFanFollowingDiffCallback

class PersonalFollowRecyclerAdapter :
    ListAdapter<PersonalFanFollowingItem, PersonalFollowRecyclerAdapter.ViewHolder>(
        PersonalFanFollowingDiffCallback
    ) {
    class ViewHolder(
        private val binding: ItemRecyclerPersonalFollowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(personalFanFollowingItem: PersonalFanFollowingItem) {
            with(binding.personalFollowItemIvAvatar) {
                GlideApp.with(context)
                    .load(personalFanFollowingItem.avatar)
                    .thumbnail(
                        GlideApp.with(context)
                            .load(R.drawable.common_ic_loading)
                    ).into(this)
            }
            binding.personalFollowItemTvName.text = personalFanFollowingItem.nickName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerPersonalFollowBinding.inflate(
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