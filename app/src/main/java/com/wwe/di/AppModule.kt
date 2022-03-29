package com.wwe.di

import com.wwe.contract.SampleConfiguration
import com.wwe.contract.SampleFeatureConfiguration
import com.wwe.config.SampleConfigurationImpl
import com.wwe.config.SampleFeatureConfigurationImpl
import com.wwe.config.SampleStrategyContractImpl
import com.wwe.contract.SampleStrategyContract
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideSampleConfiguration(
        @Named("papi") papiRetrofit: Retrofit,
        featureConfiguration: SampleFeatureConfiguration
    ): SampleConfiguration {
        return SampleConfigurationImpl(papiRetrofit, featureConfiguration)
    }

    @Provides
    fun provideSampleFeatureConfiguration(): SampleFeatureConfiguration {
        return SampleFeatureConfigurationImpl()
    }

    @Provides
    @Singleton
    fun provideSampleStrategyContract(): SampleStrategyContract {
        return SampleStrategyContractImpl()
    }
}