package com.sameershelar.currencyconverterkmm.converter.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ConversionRatesDTO(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: Map<String, Double>,
    val timestamp: Int
)