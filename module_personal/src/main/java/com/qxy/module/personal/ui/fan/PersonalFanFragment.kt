package com.qxy.module.personal.ui.fan

import android.os.Bundle
import android.view.View
import com.qxy.lib.base.base.view.fragment.BaseVmBindFragment
import com.qxy.module.personal.databinding.FragmentPersonalFanBinding

class PersonalFanFragment private constructor() :
    BaseVmBindFragment<PersonalFanViewModel, FragmentPersonalFanBinding>(){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance() = PersonalFanFragment()
    }
}