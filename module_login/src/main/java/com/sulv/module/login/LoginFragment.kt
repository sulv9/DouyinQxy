package com.sulv.module.login

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.qxy.api.account.IAccountService
import com.qxy.lib.base.ext.setLightStatusBar
import com.qxy.lib.base.base.view.fragment.BaseBindFragment
import com.qxy.lib.base.util.ARouterUtil
import com.qxy.lib.common.config.RouteTable
import com.sulv.module.login.databinding.FragmentLoginBinding

@Route(path = RouteTable.LOGIN_ENTRY)
class LoginFragment : BaseBindFragment<FragmentLoginBinding>() {

    private val mAccountService by lazy { ARouterUtil.getService(IAccountService::class) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setStatusBarWhite()
        // 点击前往登录
        binding.loginTvGoLogin.setOnClickListener {
            mAccountService.login(DouYinOpenApiFactory.create(requireActivity()))
        }
        // 监听登录状态的变化
        mAccountService.observeLoginState(viewLifecycleOwner) { loginState ->
            when {
                loginState.isLogIn -> {
                    // 登录成功
                    binding.loginTvGoLogin.text = getString(R.string.login_tv_text_login_success)
                    // TODO 登录成功动画
                    mAccountService.sendChangeFragmentRequest(IAccountService.LoginAction(logIn = true))
                }
                loginState.isLogFail -> {
                    // 登录失败
                    binding.loginTvGoLogin.text = getString(R.string.login_tv_text_login_fail)
                    // TODO 登录失败动画
                }
            }
        }
    }

    private fun setStatusBarWhite() {
        with(requireActivity()) {
            window.statusBarColor = ContextCompat.getColor(this, com.qxy.lib.common.R.color.white)
            setLightStatusBar()
        }
    }

}