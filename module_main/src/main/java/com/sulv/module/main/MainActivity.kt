package com.sulv.module.main

import android.graphics.Typeface
import com.qxy.api.account.IAccountService
import com.qxy.lib.base.base.view.activity.BaseBindActivity
import com.qxy.lib.base.base.view.adapter.HandleFragment
import com.qxy.lib.base.base.view.adapter.SimpleViewPagerAdapter
import com.qxy.lib.base.util.ARouterUtil
import com.qxy.lib.common.config.RouteTable
import com.sulv.module.main.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindActivity<ActivityMainBinding>() {

    private val mAccountService = ARouterUtil.getService(IAccountService::class)

    private enum class ChildFragments(val fragment: HandleFragment) {
        LoginFragment({ ARouterUtil.getFragment(RouteTable.LOGIN_ENTRY) }),
        RankFragment({ ARouterUtil.getFragment(RouteTable.RANK_ENTRY) }),
        PersonalFragment({ ARouterUtil.getFragment(RouteTable.PERSONAL_ENTRY) }),
    }

    override fun initView() {
        Thread.sleep(3000)
        // 初始化viewpager
        val pagerAdapter = SimpleViewPagerAdapter(supportFragmentManager, lifecycle)
        ChildFragments.values()
            .forEach { childFragment -> pagerAdapter.add(childFragment.fragment) }
        binding.mainVp.adapter = pagerAdapter
        binding.mainVp.isUserInputEnabled = false // 禁止滑动 TODO 解决滑动冲突
        // 初始化显示Fragment的内容
        changeToCheckedFragment(
            binding.mainBottomRg.checkedRadioButtonId,
            mAccountService.isLogin()
        )
        // 监听底部RadioGroup点击变化
        binding.mainBottomRg.setOnCheckedChangeListener { _, checkedId ->
            // 设置按钮选中时文字加粗
            listOf(binding.mainBottomRank, binding.mainBottomPersonal).forEach {
                it.typeface = if (it.isChecked) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            }
            // 点击切换fragment
            changeToCheckedFragment(checkedId, mAccountService.isLogin())
        }
    }

    override fun initData() {
        // 监听登录行为来切换Fragment
        mAccountService.observeFragmentChange(this) { loginAction ->
            when {
                loginAction.logIn -> {
                    changeToCheckedFragment(
                        binding.mainBottomRg.checkedRadioButtonId,
                        true
                    )
                }
                loginAction.logOut -> binding.mainVp.setCurrentItem(
                    ChildFragments.LoginFragment.ordinal,
                    false
                )
            }
        }
    }

    /**
     * 切换到底部选中的Fragment
     */
    private fun changeToCheckedFragment(checkId: Int, isLogin: Boolean) {
        when (checkId) {
            R.id.main_bottom_rank -> {
                binding.mainVp.setCurrentItem(
                    if (!isLogin)
                        ChildFragments.LoginFragment.ordinal
                    else
                        ChildFragments.RankFragment.ordinal, false
                )
            }
            R.id.main_bottom_personal -> {
                binding.mainVp.setCurrentItem(
                    if (!isLogin)
                        ChildFragments.LoginFragment.ordinal
                    else
                        ChildFragments.PersonalFragment.ordinal, false
                )
            }
        }
    }

}