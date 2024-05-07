package com.sameershelar.currencyconverterkmm.converter.presentation

import com.sameershelar.currencyconverterkmm.converter.presentation.model.UICurrency

sealed class CurrencyConversionEvents {
    data class ChooseFromCurrency(val currency: UICurrency) : CurrencyConversionEvents()
    object StopChoosingCurrency : CurrencyConversionEvents()
    data class ChangeAmount(val amount: Int) : CurrencyConversionEvents()
    object OpenFromCurrencyDropDown : CurrencyConversionEvents()
    object SyncData : CurrencyConversionEvents()
    object OnErrorSeen : CurrencyConversionEvents()
}
