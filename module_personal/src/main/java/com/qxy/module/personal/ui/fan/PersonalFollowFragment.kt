package com.qxy.module.personal.ui.fan

import android.os.Bundle
import android.view.View
import com.qxy.lib.base.base.view.fragment.BaseVmBindFragment
import com.qxy.module.personal.databinding.FragmentPersonalFollowBinding

class PersonalFollowFragment private constructor():
    BaseVmBindFragment<PersonalFollowViewModel, FragmentPersonalFollowBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance() = PersonalFollowFragment()
    }
}