package com.sulv.module.main

import android.graphics.Typeface
import androidx.fragment.app.commit
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.ui.activity.BaseBindActivity
import com.qxy.lib.base.util.ARouterUtil
import com.qxy.lib.common.config.RouteTable
import com.sulv.module.main.databinding.ActivityMainBinding

class MainActivity : BaseBindActivity<ActivityMainBinding>() {

    override val isStatusBarTransparent = true

    override val isPortraitScreen: Boolean
        get() = false

    override fun initView() {
        binding.mainBottomRg.setOnCheckedChangeListener { _, checkedId ->
            // 设置按钮选中时文字加粗
            listOf(binding.mainBottomRank, binding.mainBottomPersonal).forEach {
                it.typeface = if (it.isChecked) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            }
            // 点击切换fragment
            when (checkedId) {
                R.id.main_bottom_rank -> {
                    log { "Click Rank" }
                    supportFragmentManager.commit {
                        replace(R.id.main_fcv, ARouterUtil.getFragment(RouteTable.RANK_ENTRY))
                    }
                }
                R.id.main_bottom_personal -> {

                }
            }
        }
    }

}