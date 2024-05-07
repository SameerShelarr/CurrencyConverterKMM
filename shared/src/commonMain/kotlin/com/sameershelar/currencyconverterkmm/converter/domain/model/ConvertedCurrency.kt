package com.sameershelar.currencyconverterkmm.converter.domain.model

data class ConvertedCurrency(
    val fromCurrencyCode: String,
    val fromCurrencyAmount: Int,
    val toCurrencyCode: String,
    val toCurrencyAmount: Double
)
