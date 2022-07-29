package com.qxy.lib.base.ui.fragment

import androidx.fragment.app.Fragment
import com.qxy.lib.base.ui.BaseView

/**
 * Activity基类，如果不需要使用binding和viewModel可以选择继承该类
 */
abstract class BaseFragment: Fragment(), BaseView {
}