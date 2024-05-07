package com.sameershelar.currencyconverterkmm.converter.presentation.mapper

import com.sameershelar.currencyconverterkmm.converter.domain.model.Currency
import com.sameershelar.currencyconverterkmm.converter.presentation.model.UICurrency

fun List<Currency>.toUiCurrencies(): List<UICurrency> {
    return this.map { UICurrency(it.currencyCode, it.currencyName) }
}