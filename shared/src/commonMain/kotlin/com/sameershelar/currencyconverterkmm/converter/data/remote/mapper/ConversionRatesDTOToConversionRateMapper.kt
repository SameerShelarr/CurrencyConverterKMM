package com.sameershelar.currencyconverterkmm.converter.data.remote.mapper

import com.sameershelar.currencyconverterkmm.converter.data.remote.model.ConversionRatesDTO
import com.sameershelar.currencyconverterkmm.converter.domain.model.ConversionRate

fun ConversionRatesDTO.toConversionRates(): List<ConversionRate> {
    return rates.map {
        ConversionRate(
            currencyCode = it.key,
            rate = it.value
        )
    }
}