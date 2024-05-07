package com.sameershelar.currencyconverterkmm.converter.domain.usecase

import com.sameershelar.currencyconverterkmm.converter.domain.model.ConvertedCurrency

class CurrencyConverterUseCase {
    operator fun invoke(
        fromCurrencyCode: String,
        fromAmount: Int,
        fromBaseRate: Double,
        toCurrencyCode: String,
        toBaseRate: Double,
        baseRate: Double
    ): ConvertedCurrency {

        val toCurrencyPerBase = toBaseRate / baseRate
        val fromCurrencyPerBase = fromBaseRate / baseRate

        val unitAmount = toCurrencyPerBase / fromCurrencyPerBase

        val convertedAmount = unitAmount * fromAmount

        return ConvertedCurrency(
            fromCurrencyCode = fromCurrencyCode,
            fromCurrencyAmount = fromAmount,
            toCurrencyCode = toCurrencyCode,
            toCurrencyAmount = convertedAmount
        )
    }
}