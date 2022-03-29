package com.wwe.detail.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wwe.base.BaseFragment
import com.wwe.util.SingleLiveEvent

interface DetailContract {

    interface DetailViewModelInput {
        fun viewDidLoad()
        fun didClickCloseButton()
        fun didClickConfirmButton()
    }

    interface DetailViewModelOutput {
        val navigationBarTitle: LiveData<String>
        val maybeLaterButtonTitle: LiveData<String>

        val navigationToDest: SingleLiveEvent
        val dismissFragment: SingleLiveEvent
        val isImageVisibility: MutableLiveData<Int>
    }

    interface DetailRouter {
        fun navigateToDestFragment(fragment: BaseFragment)
        fun dismissFragment(fragment: BaseFragment)
    }
}