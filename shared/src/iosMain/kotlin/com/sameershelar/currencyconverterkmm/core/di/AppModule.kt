package com.sameershelar.currencyconverterkmm.core.di

import com.sameershelar.currencyconverterkmm.ConversionsDatabase
import com.sameershelar.currencyconverterkmm.converter.data.local.CurrencyConversionLocalStorage
import com.sameershelar.currencyconverterkmm.converter.data.local.DatabaseDriverFactory
import com.sameershelar.currencyconverterkmm.converter.data.local.SqDelightCurrencyConversionDataSource
import com.sameershelar.currencyconverterkmm.converter.data.remote.HttpClientFactory
import com.sameershelar.currencyconverterkmm.converter.data.remote.KtorCurrencyConversionClient
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionClient
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionsDataSource
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.ConversionRatesAndAllCurrenciesDataSyncUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.CurrencyConverterUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.GetAllConversionsUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.IsEligibleForDataSyncUseCase
import com.sameershelar.currencyconverterkmm.core.data.KmmPreferences
import com.sameershelar.currencyconverterkmm.core.data.SPref

class AppModule {

    val dataSource: ICurrencyConversionsDataSource by lazy {
        SqDelightCurrencyConversionDataSource(
            db = ConversionsDatabase(
                driver = DatabaseDriverFactory().create()
            )
        )
    }

    private val client: ICurrencyConversionClient by lazy {
        KtorCurrencyConversionClient(
            httpClient = HttpClientFactory().create()
        )
    }

    val dataSyncUseCase: ConversionRatesAndAllCurrenciesDataSyncUseCase by lazy {
        ConversionRatesAndAllCurrenciesDataSyncUseCase(
            currencyConversionClient = client,
            currencyConversionsDataSource = dataSource,
            localStorage = CurrencyConversionLocalStorage(
                prefs = KmmPreferences(prefs = SPref())
            ),
            isEligibleForDataSyncUseCase = IsEligibleForDataSyncUseCase()
        )
    }

    val getAllConversionsUseCase: GetAllConversionsUseCase by lazy {
        GetAllConversionsUseCase(
            currencyConverterUseCase = CurrencyConverterUseCase()
        )
    }
}