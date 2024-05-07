package com.sameershelar.currencyconverterkmm.android.di

import android.app.Application
import com.sameershelar.currencyconverterkmm.ConversionsDatabase
import com.sameershelar.currencyconverterkmm.converter.data.local.CurrencyConversionLocalStorage
import com.sameershelar.currencyconverterkmm.converter.data.local.DatabaseDriverFactory
import com.sameershelar.currencyconverterkmm.converter.data.local.SqDelightCurrencyConversionDataSource
import com.sameershelar.currencyconverterkmm.converter.data.remote.KtorCurrencyConversionClient
import com.sameershelar.currencyconverterkmm.converter.data.remote.HttpClientFactory
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionClient
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionLocalStorage
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionsDataSource
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.ConversionRatesAndAllCurrenciesDataSyncUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.CurrencyConverterUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.GetAllConversionsUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.IsEligibleForDataSyncUseCase
import com.sameershelar.currencyconverterkmm.core.data.KmmPreferences
import com.sameershelar.currencyconverterkmm.core.data.SPref
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideConverterClient(httpClient: HttpClient): ICurrencyConversionClient {
        return KtorCurrencyConversionClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): ICurrencyConversionsDataSource {
        return SqDelightCurrencyConversionDataSource(ConversionsDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideKmmPreferences(sPref: SPref): KmmPreferences {
        return KmmPreferences(sPref)
    }

    @Provides
    @Singleton
    fun provideCurrencyConversionLocalStorage(
        kmmPreferences: KmmPreferences
    ): ICurrencyConversionLocalStorage {
        return CurrencyConversionLocalStorage(kmmPreferences)
    }

    @Provides
    @Singleton
    fun provideIsEligibleForDataSyncUseCase(): IsEligibleForDataSyncUseCase {
        return IsEligibleForDataSyncUseCase()
    }

    @Provides
    @Singleton
    fun provideConversionRatesAndAllCurrenciesDataSyncUseCase(
        client: ICurrencyConversionClient,
        dataSource: ICurrencyConversionsDataSource,
        localStorage: ICurrencyConversionLocalStorage,
        isEligibleForDataSyncUseCase: IsEligibleForDataSyncUseCase
    ): ConversionRatesAndAllCurrenciesDataSyncUseCase {
        return ConversionRatesAndAllCurrenciesDataSyncUseCase(
            currencyConversionClient = client,
            currencyConversionsDataSource = dataSource,
            localStorage = localStorage,
            isEligibleForDataSyncUseCase = isEligibleForDataSyncUseCase
        )
    }

    @Provides
    @Singleton
    fun providesCurrencyConverterUseCase(): CurrencyConverterUseCase {
        return CurrencyConverterUseCase()
    }

    @Provides
    @Singleton
    fun provideGetAllConversionsUseCase(
        currencyConverterUseCase: CurrencyConverterUseCase
    ): GetAllConversionsUseCase {
        return GetAllConversionsUseCase(
            currencyConverterUseCase = currencyConverterUseCase
        )
    }
}