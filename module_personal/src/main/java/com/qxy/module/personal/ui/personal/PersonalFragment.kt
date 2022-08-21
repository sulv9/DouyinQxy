package com.qxy.module.personal.ui.personal

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.qxy.lib.base.base.network.whenFailure
import com.qxy.lib.base.base.network.whenLoading
import com.qxy.lib.base.base.network.whenNone
import com.qxy.lib.base.base.network.whenSuccess
import com.qxy.lib.base.base.view.fragment.BaseVmBindFragment
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.ext.setLightStatusBar
import com.qxy.lib.base.ext.toast
import com.qxy.lib.common.config.RouteTable
import com.qxy.module.personal.databinding.FragmentPersonalBinding
import com.qxy.module.personal.ui.fanfollow.PersonalFanFollowActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@Route(path = RouteTable.PERSONAL_ENTRY)
@AndroidEntryPoint
class PersonalFragment : BaseVmBindFragment<PersonalViewModel, FragmentPersonalBinding>() {

    private val tabName = arrayOf("作品", "私密", "收藏", "喜欢")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Thread.sleep(3000L)
        setupStatusBar()
        initView()
        flowObserve()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initialize()
    }

    private fun initView() {
        binding.personalTvFansInfo.setOnClickListener {
            binding.personalTvFollowInfo.setOnClickListener {
                log { "click follow" }
                PersonalFanFollowActivity.startFanFollowActivity(requireActivity(), 0)
            }
            binding.personalTvFollowData.setOnClickListener {
                PersonalFanFollowActivity.startFanFollowActivity(requireActivity(), 0)
            }
            PersonalFanFollowActivity.startFanFollowActivity(requireActivity(), 1)
        }
        binding.personalTvFansData.setOnClickListener {
            PersonalFanFollowActivity.startFanFollowActivity(requireActivity(), 1)
        }
        binding.vpPersonal.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = tabName.size

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> PersonalVideoFragment()
                    else -> Fragment()
                }
            }
        }
        TabLayoutMediator(binding.tlPersonal, binding.vpPersonal) { tab, pos ->
            tab.text = tabName[pos]
        }.attach()
    }

    private fun flowObserve() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.personal.collectLatest { result ->

                // 没有相关接口，先写死
                // 没有简介学校，暂时隐藏
//                binding.personalTvDesc.isGone = true
//                binding.tvSchool.isGone = true

                result.whenSuccess { info ->
                    Glide.with(binding.root).load(info.avatar).into(binding.personalIvUserAvatar)
                    binding.personalTvName.text = info.nickname
//                    binding.personalTvDouyinNum.text = "抖音号：${info.openID}"
//                    binding.tvGender.text = when (info.gender) {
//                        1 -> "♂ 未知"
//                        2 -> "♀ 未知"
//                        else -> "未知"
//                    }
                    return@whenSuccess
                }
                result.whenFailure { e ->
                    e.localizedMessage?.let(::toast)
                    return@whenFailure
                }
                result.whenLoading {
                    return@whenLoading
                }
                result.whenNone {
                    return@whenNone
                }
            }
        }
    }

    private fun setupStatusBar() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), com.qxy.lib.common.R.color.transparent)
        with(requireActivity()) {
            window.statusBarColor = ContextCompat.getColor(
                this, com.qxy.lib.common.R.color.transparent
            )
            setLightStatusBar()
        }
    }
}