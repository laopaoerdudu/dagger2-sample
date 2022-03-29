package com.wwe.detail.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wwe.contract.SampleConfiguration
import com.wwe.detail.contract.DetailContract
import com.wwe.util.SingleLiveEvent
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    val sampleConfiguration: SampleConfiguration
) : DetailContract.DetailViewModelInput,
    DetailContract.DetailViewModelOutput {
    lateinit var context: Context

    override fun viewDidLoad() {
        // TODO: update it in next phase
    }

    override fun didClickCloseButton() {
        dismissFragment.call()
    }

    override fun didClickConfirmButton() {
        navigationToDest.call()
    }

    override val navigationBarTitle: LiveData<String> by lazy {
        MutableLiveData("dagger2-sample")
    }

    override val maybeLaterButtonTitle: LiveData<String> by lazy {
        MutableLiveData("Maybe later")
    }

    override val navigationToDest: SingleLiveEvent by lazy {
        SingleLiveEvent()
    }

    override val dismissFragment: SingleLiveEvent by lazy {
        SingleLiveEvent()
    }

    override val isImageVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData(View.GONE)
    }
}