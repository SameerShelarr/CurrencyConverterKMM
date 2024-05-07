package com.sameershelar.currencyconverterkmm.converter.data.local.mapper

import com.sameershelar.currencyconverterkmm.converter.domain.model.Currency
import database.CurrencyEntity

fun CurrencyEntity.toAllCurrencies(): Currency {
    return Currency(
        currencyCode = currencyCode,
        currencyName = currencyName
    )
}