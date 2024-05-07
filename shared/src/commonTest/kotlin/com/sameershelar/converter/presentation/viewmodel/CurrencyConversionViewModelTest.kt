package com.sameershelar.converter.presentation.viewmodel

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import com.sameershelar.converter.data.local.FakeCurrencyConversionDataSource
import com.sameershelar.converter.data.local.FakeCurrencyConversionLocalStorage
import com.sameershelar.converter.data.remote.FakeCurrencyConversionClient
import com.sameershelar.currencyconverterkmm.converter.domain.model.ConversionRate
import com.sameershelar.currencyconverterkmm.converter.domain.model.Currency
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.ConversionRatesAndAllCurrenciesDataSyncUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.CurrencyConverterUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.GetAllConversionsUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.IsEligibleForDataSyncUseCase
import com.sameershelar.currencyconverterkmm.converter.presentation.CurrencyConversionEvents
import com.sameershelar.currencyconverterkmm.converter.presentation.CurrencyConversionState
import com.sameershelar.currencyconverterkmm.converter.presentation.model.UICurrency
import com.sameershelar.currencyconverterkmm.converter.presentation.viewmodel.CurrencyConversionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class CurrencyConversionViewModelTest {

    private lateinit var viewModel: CurrencyConversionViewModel
    private lateinit var fakeDataSource: FakeCurrencyConversionDataSource
    private lateinit var fakeClient: FakeCurrencyConversionClient
    private lateinit var fakeLocalStorage: FakeCurrencyConversionLocalStorage

    @BeforeTest
    fun setup() {
        fakeDataSource = FakeCurrencyConversionDataSource()
        fakeClient = FakeCurrencyConversionClient()
        fakeLocalStorage = FakeCurrencyConversionLocalStorage()
        val fakeDataSyncUseCase = ConversionRatesAndAllCurrenciesDataSyncUseCase(
            currencyConversionClient = fakeClient,
            currencyConversionsDataSource = fakeDataSource,
            localStorage = fakeLocalStorage,
            isEligibleForDataSyncUseCase = IsEligibleForDataSyncUseCase()
        )

        viewModel = CurrencyConversionViewModel(
            dataSyncUseCase = fakeDataSyncUseCase,
            getAllConversionsUseCase = GetAllConversionsUseCase(
                currencyConverterUseCase = CurrencyConverterUseCase()
            ),
            dataSource = fakeDataSource,
            coroutineScope = CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `State and Currencies and ConversionRates are properly combined`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(CurrencyConversionState())

            val rate = ConversionRate(
                currencyCode = "USD",
                rate = 1.0
            )
            val currency = Currency(
                currencyCode = "INR",
                currencyName = "Indian Rupee"
            )

            fakeDataSource.insertConversionRates(rate)
            fakeDataSource.insertCurrency(currency)

            val state = awaitItem()

            val expectedRate = ConversionRate(
                currencyCode = rate.currencyCode,
                rate = rate.rate
            )

            val expectedCurrency = UICurrency(
                currencyCode = currency.currencyCode,
                currencyName = currency.currencyName
            )

            assertThat(state.conversionRate.first()).isEqualTo(expectedRate)
            assertThat(state.allCurrencies.first()).isEqualTo(expectedCurrency)
        }
    }

    @Test
    fun `When amount is changed in the Text Field then appropriate state is launched with the new amount`() =
        runBlocking {
            viewModel.state.test {
                awaitItem()
                val amount = 1
                viewModel.onEvent(CurrencyConversionEvents.ChangeAmount(amount = amount))

                val resultState = awaitItem()
                assertThat(resultState.isConverting).isFalse()
                assertThat(resultState.fromAmount).isEqualTo(amount)
            }
        }

    @Test
    fun `When the currency is selected then appropriate state is launched with the new currency`() =
        runBlocking {
            viewModel.state.test {
                val selectedCurrency = UICurrency(
                    currencyCode = "USD",
                    currencyName = "United States Dollar"
                )
                viewModel.onEvent(
                    event = CurrencyConversionEvents.ChooseFromCurrency(
                        currency = selectedCurrency
                    )
                )
                val currencyChosenState = awaitItem()
                assertThat(currencyChosenState.isChoosingCurrencyCode).isFalse()
                assertThat(currencyChosenState.fromCurrency).isEqualTo(
                    UICurrency(
                        currencyCode = selectedCurrency.currencyCode,
                        currencyName = selectedCurrency.currencyName
                    )
                )
            }
        }
}