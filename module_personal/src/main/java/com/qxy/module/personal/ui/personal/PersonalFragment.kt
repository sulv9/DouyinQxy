package com.qxy.module.personal.ui.personal

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.qxy.lib.base.base.view.fragment.BaseVmBindFragment
import com.qxy.lib.common.config.RouteTable
import com.qxy.module.personal.databinding.FragmentPersonalBinding

@Route(path = RouteTable.PERSONAL_ENTRY)
class PersonalFragment : BaseVmBindFragment<PersonalViewModel, FragmentPersonalBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}