package com.qxy.module.personal.ui.fanfollow

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.qxy.lib.base.base.view.activity.BaseBindActivity
import com.qxy.lib.base.base.view.adapter.SimpleViewPagerAdapter
import com.qxy.lib.base.ext.setLightStatusBar
import com.qxy.module.personal.R
import com.qxy.module.personal.databinding.ActivityPersonalFanFollowBinding
import com.qxy.module.personal.ui.fanfollow.fan.PersonalFanFragment
import com.qxy.module.personal.ui.fanfollow.follow.PersonalFollowFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalFanFollowActivity : BaseBindActivity<ActivityPersonalFanFollowBinding>() {
    private val pages by lazy {
        mapOf(
            0 to getString(R.string.personal_string_follow),
            1 to getString(R.string.personal_string_fans),
        )
    }

    override fun initView() {
        setupStatusBar()
        binding.personalFanVp.adapter = SimpleViewPagerAdapter(supportFragmentManager, lifecycle)
            .apply {
                add { PersonalFollowFragment.newInstance() }
                add { PersonalFanFragment.newInstance() }
            }
        TabLayoutMediator(binding.personalFanTl, binding.personalFanVp) { tab, position ->
            tab.text = pages[position]
        }.attach()
        binding.personalFanIvBack.setOnClickListener { finish() }
    }

    override fun initData() {
        when (intent.extras?.getInt(KEY_PERSONAL_FAN_EXTRAS) ?: 0) {
            0 -> binding.personalFanVp.setCurrentItem(0, false)
            1 -> binding.personalFanVp.setCurrentItem(1, false)
        }
    }

    private fun setupStatusBar() {
        window.statusBarColor = ContextCompat.getColor(
            this, com.qxy.lib.common.R.color.white
        )
        setLightStatusBar()
    }

    companion object {
        private const val KEY_PERSONAL_FAN_EXTRAS = "key_personal_fan_extras"
        fun startFanFollowActivity(context: Context, type: Int) {
            context.startActivity(Intent(context, this::class.java).apply {
                putExtra(KEY_PERSONAL_FAN_EXTRAS, type)
            })
        }
    }
}