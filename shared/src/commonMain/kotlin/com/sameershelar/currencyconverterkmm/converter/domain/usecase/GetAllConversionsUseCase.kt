package com.sameershelar.currencyconverterkmm.converter.domain.usecase

import com.sameershelar.currencyconverterkmm.converter.domain.model.ConversionRate
import com.sameershelar.currencyconverterkmm.converter.domain.model.ConvertedCurrency
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.DEFAULT_BASE_CURRENCY
import com.sameershelar.currencyconverterkmm.core.domain.util.ConversionError
import com.sameershelar.currencyconverterkmm.core.domain.util.ConversionException
import com.sameershelar.currencyconverterkmm.core.domain.util.Resource

class GetAllConversionsUseCase(
    private val currencyConverterUseCase: CurrencyConverterUseCase
) {
    operator fun invoke(
        conversionRate: List<ConversionRate>,
        fromCurrencyCode: String,
        fromAmount: Int
    ): Resource<List<ConvertedCurrency>> {
        val baseConversionRate =
            conversionRate.firstOrNull { it.currencyCode == DEFAULT_BASE_CURRENCY }?.rate
        val baseForFromCurrency =
            conversionRate.firstOrNull { it.currencyCode == fromCurrencyCode }?.rate

        baseConversionRate?.let { baseRate ->
            baseForFromCurrency?.let { fromRate ->

                return Resource.Success(
                    conversionRate.map {
                        currencyConverterUseCase(
                            fromCurrencyCode = fromCurrencyCode,
                            fromAmount = fromAmount,
                            fromBaseRate = fromRate,
                            toCurrencyCode = it.currencyCode,
                            toBaseRate = it.rate,
                            baseRate = baseRate
                        )
                    }
                )

            } ?: kotlin.run {
                return Resource.Error(
                    ConversionException(ConversionError.CONVERSION_RATE_UNAVAILABLE)
                )
            }
        } ?: kotlin.run {
            return Resource.Error(
                ConversionException(ConversionError.CONVERSION_RATE_UNAVAILABLE)
            )
        }
    }
}