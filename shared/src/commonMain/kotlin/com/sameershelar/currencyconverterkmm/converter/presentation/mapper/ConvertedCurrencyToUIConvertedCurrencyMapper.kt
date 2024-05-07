package com.sameershelar.currencyconverterkmm.converter.presentation.mapper

import com.sameershelar.currencyconverterkmm.converter.domain.model.ConvertedCurrency
import com.sameershelar.currencyconverterkmm.converter.presentation.model.UIConvertedCurrency

fun ConvertedCurrency.toUIConvertedCurrency(): UIConvertedCurrency {
    return UIConvertedCurrency(
        fromCurrencyCode = fromCurrencyCode,
        fromCurrencyAmount = fromCurrencyAmount,
        toCurrencyCode = toCurrencyCode,
        toCurrencyAmount = toCurrencyAmount
    )
}