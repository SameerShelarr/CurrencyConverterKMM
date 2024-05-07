package com.sameershelar.converter.data.remote

import com.sameershelar.currencyconverterkmm.converter.data.remote.model.ConversionRatesDTO
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionClient

class FakeCurrencyConversionClient : ICurrencyConversionClient {

    private val fakeConversionRatesDTO = ConversionRatesDTO(
        base = "USD",
        disclaimer = "",
        license = "",
        rates = emptyMap(),
        timestamp = 0
    )

    private val fakeCurrencies = emptyMap<String, String>()

    override suspend fun getLatestConversionRates(currencies: String): ConversionRatesDTO {
        return fakeConversionRatesDTO
    }

    override suspend fun getAllCurrencies(): Map<String, String> {
        return fakeCurrencies
    }
}