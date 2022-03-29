package com.wwe.config

import com.wwe.contract.SampleConfiguration
import com.wwe.contract.SampleFeatureConfiguration
import retrofit2.Retrofit

class SampleConfigurationImpl(
    override val retrofit: Retrofit,
    override val featureConfiguration: SampleFeatureConfiguration
) : SampleConfiguration