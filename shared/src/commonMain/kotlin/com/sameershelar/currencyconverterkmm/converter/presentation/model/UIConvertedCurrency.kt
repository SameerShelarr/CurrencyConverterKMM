package com.sameershelar.currencyconverterkmm.converter.presentation.model

data class UIConvertedCurrency(
    val fromCurrencyCode: String,
    val fromCurrencyAmount: Int,
    val toCurrencyCode: String,
    val toCurrencyAmount: Double
)
