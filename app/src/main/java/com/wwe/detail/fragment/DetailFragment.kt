package com.wwe.detail.fragment

import androidx.databinding.ViewDataBinding
import com.wwe.R
import com.wwe.base.BaseFragment
import com.wwe.databinding.FragmentEnterDetailsBinding

class DetailFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.fragment_enter_details

    override val bindingLogicCallback: (binding: ViewDataBinding) -> Unit = {
        (it as FragmentEnterDetailsBinding).apply {

        }
    }


}