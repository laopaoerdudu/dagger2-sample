package com.wwe.contract

import retrofit2.Retrofit

interface SampleConfiguration {
    val retrofit: Retrofit
    val featureConfiguration: SampleFeatureConfiguration
}