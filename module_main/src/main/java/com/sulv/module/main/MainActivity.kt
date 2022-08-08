package com.sulv.module.main

import android.graphics.Typeface
import com.qxy.api.account.IAccountService
import com.qxy.lib.base.base.view.activity.BaseBindActivity
import com.qxy.lib.base.util.ARouterUtil
import com.qxy.lib.common.config.RouteTable
import com.sulv.module.main.databinding.ActivityMainBinding

class MainActivity : BaseBindActivity<ActivityMainBinding>() {

    override val isStatusBarTransparent = true

    override val isPortraitScreen: Boolean
        get() = false

    private val mAccountService = ARouterUtil.getService(IAccountService::class)

    private val loginFragment by lazy { ARouterUtil.getFragment(RouteTable.LOGIN_ENTRY) }
    private val rankFragment by lazy { ARouterUtil.getFragment(RouteTable.RANK_ENTRY) }
    private val personalFragment by lazy { ARouterUtil.getFragment(RouteTable.PERSONAL_ENTRY) }

    override fun initView() {
        // 初始化显示Fragment的内容
        changeToCheckedFragment(binding.mainBottomRg.checkedRadioButtonId, mAccountService.isLogin())
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
                loginAction.logOut -> replaceFragment(R.id.main_fcv) { loginFragment }
            }
        }
    }

    /**
     * 切换到底部选中的Fragment
     */
    private fun changeToCheckedFragment(checkId: Int, isLogin: Boolean) {
        when (checkId) {
            R.id.main_bottom_rank -> {
                replaceFragment(R.id.main_fcv) {
                    if (isLogin) ARouterUtil.getFragment(RouteTable.RANK_ENTRY) else loginFragment
                }
            }
            R.id.main_bottom_personal -> {
                replaceFragment(R.id.main_fcv) {
                    if (isLogin) personalFragment else loginFragment
                }
            }
        }
    }

}