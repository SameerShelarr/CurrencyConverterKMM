package com.sameershelar.currencyconverterkmm.converter.domain.usecase

import com.sameershelar.currencyconverterkmm.converter.data.remote.mapper.toConversionRates
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionClient
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionLocalStorage
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionsDataSource
import com.sameershelar.currencyconverterkmm.converter.domain.model.Currency
import kotlinx.datetime.Clock

class ConversionRatesAndAllCurrenciesDataSyncUseCase(
    private val currencyConversionClient: ICurrencyConversionClient,
    private val currencyConversionsDataSource: ICurrencyConversionsDataSource,
    private val localStorage: ICurrencyConversionLocalStorage,
    private val isEligibleForDataSyncUseCase: IsEligibleForDataSyncUseCase
) {
    suspend operator fun invoke() {
        // if last data sync was within 30 minutes don't sync data.
        if (
            isEligibleForDataSyncUseCase(
                lastSyncTimeStamp = localStorage.getLastDataSyncTimeStamp(),
                currentTimeStamp = Clock.System.now().toEpochMilliseconds()
            ).not()
        ) return

        try {
            // Fetching and saving all the supported currencies
            val allCurrencies = currencyConversionClient.getAllCurrencies()
                .map {
                    Currency(
                        currencyCode = it.key,
                        currencyName = it.value
                    )
                }
            currencyConversionsDataSource.clearOldCurrencies()
            allCurrencies.forEach {
                currencyConversionsDataSource.insertCurrency(it)
            }

            // Fetching and saving all the latest exchange rates
            val conversionRates = currencyConversionClient.getLatestConversionRates(
                currencies = allCurrencies.joinToString(",") { it.currencyCode }
            ).toConversionRates()
            currencyConversionsDataSource.clearOldConversionRates()
            conversionRates.forEach {
                currencyConversionsDataSource.insertConversionRates(it)
            }

            localStorage.setLastDataSyncTimeStamp(
                timeStamp = Clock.System.now().toEpochMilliseconds()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}