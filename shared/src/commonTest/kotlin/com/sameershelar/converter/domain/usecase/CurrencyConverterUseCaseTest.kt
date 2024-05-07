package com.sameershelar.converter.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.sameershelar.currencyconverterkmm.converter.domain.model.ConvertedCurrency
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.CurrencyConverterUseCase
import kotlin.test.BeforeTest
import kotlin.test.Test

class CurrencyConverterUseCaseTest {

    private lateinit var useCase: CurrencyConverterUseCase

    @BeforeTest
    fun setup() {
        useCase = CurrencyConverterUseCase()
    }

    @Test
    fun `Converts the currency as per the data given` () {

        val expected = ConvertedCurrency(
            fromCurrencyCode = "AED",
            fromCurrencyAmount = 1,
            toCurrencyCode = "INR",
            toCurrencyAmount = 22.446866485013622
        )

        assertThat(
            useCase.invoke(
                fromCurrencyCode = "AED",
                fromAmount = 1,
                fromBaseRate = 3.67,
                toCurrencyCode = "INR",
                toBaseRate = 82.38,
                baseRate = 1.0
            )
        ).isEqualTo(
            expected
        )
    }
}