package com.wwe.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.wwe.contract.SampleStrategyContract
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var strategyContract: SampleStrategyContract

    abstract val layoutRes: Int

    abstract val bindingLogicCallback: (binding: ViewDataBinding) -> Unit

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewDataBinding =
            DataBindingUtil.inflate<ViewDataBinding?>(layoutInflater, layoutRes, container, false)
                .apply {
                    lifecycleOwner = this@BaseFragment
                    bindingLogicCallback(this)
                }
        return viewDataBinding.root
    }
}