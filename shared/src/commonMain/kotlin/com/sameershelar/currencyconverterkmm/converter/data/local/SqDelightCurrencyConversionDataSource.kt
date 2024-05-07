package com.sameershelar.currencyconverterkmm.converter.data.local

import com.sameershelar.currencyconverterkmm.ConversionsDatabase
import com.sameershelar.currencyconverterkmm.converter.data.local.mapper.toAllCurrencies
import com.sameershelar.currencyconverterkmm.converter.data.local.mapper.toConversionRates
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionsDataSource
import com.sameershelar.currencyconverterkmm.converter.domain.model.ConversionRate
import com.sameershelar.currencyconverterkmm.converter.domain.model.Currency
import com.sameershelar.currencyconverterkmm.core.domain.util.CommonFlow
import com.sameershelar.currencyconverterkmm.core.domain.util.toCommonFlow
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map

class SqDelightCurrencyConversionDataSource(
    db: ConversionsDatabase
) : ICurrencyConversionsDataSource {

    private val queries = db.conversionQueries

    override suspend fun insertConversionRates(rate: ConversionRate) {
        queries.insertConversionRateEntity(
            id = null,
            currencyCode = rate.currencyCode,
            rate = rate.rate
        )
    }

    override fun getLatestConversionRates(): CommonFlow<List<ConversionRate>> {
        return queries.getLatestConversionRates()
            .asFlow()
            .mapToList()
            .map { conversionRates ->
                conversionRates.map { it.toConversionRates() }
            }
            .toCommonFlow()
    }

    override suspend fun insertCurrency(currency: Currency) {
        queries.insertCurrencyEntity(
            id = null,
            currencyCode = currency.currencyCode,
            currencyName = currency.currencyName
        )
    }

    override fun getAllCurrencies(): CommonFlow<List<Currency>> {
        return queries.getAllCurrencies()
            .asFlow()
            .mapToList()
            .map { currencies ->
                currencies.map { it.toAllCurrencies() }
            }
            .toCommonFlow()
    }

    override suspend fun clearOldConversionRates() {
        queries.clearConversionRates()
    }

    override suspend fun clearOldCurrencies() {
        queries.clearCurrencies()
    }

}