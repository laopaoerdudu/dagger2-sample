package com.wwe.config

import com.wwe.contract.SampleStrategyContract

class SampleStrategyContractImpl : SampleStrategyContract {
    override fun isWPB(): Boolean {
        return false
    }
}