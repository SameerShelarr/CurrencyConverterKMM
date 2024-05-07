package com.sameershelar.currencyconverterkmm.converter.data.local.mapper

import com.sameershelar.currencyconverterkmm.converter.domain.model.ConversionRate
import database.ConversionRateEntity

fun ConversionRateEntity.toConversionRates(): ConversionRate {
    return ConversionRate(
        currencyCode = currencyCode,
        rate = rate
    )
}