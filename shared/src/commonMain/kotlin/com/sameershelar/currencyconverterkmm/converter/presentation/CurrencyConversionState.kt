package com.sameershelar.currencyconverterkmm.converter.presentation

import com.sameershelar.currencyconverterkmm.converter.domain.model.ConversionRate
import com.sameershelar.currencyconverterkmm.converter.presentation.model.UIConvertedCurrency
import com.sameershelar.currencyconverterkmm.converter.presentation.model.UICurrency
import com.sameershelar.currencyconverterkmm.core.domain.util.ConversionError

data class CurrencyConversionState(
    val fromAmount: Int = 0,
    val isConverting: Boolean = false,
    val fromCurrency: UICurrency = UICurrency(
        currencyCode = "USD",
        currencyName = "United States Dollar"
    ),
    val isChoosingCurrencyCode: Boolean = false,
    val error: ConversionError? = null,
    val conversionRate: List<ConversionRate> = emptyList(),
    val allCurrencies: List<UICurrency> = emptyList(),
    val allConversions: List<UIConvertedCurrency>? = null
)
