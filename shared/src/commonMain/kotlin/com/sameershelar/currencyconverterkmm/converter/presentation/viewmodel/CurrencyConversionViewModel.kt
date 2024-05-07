package com.sameershelar.currencyconverterkmm.converter.presentation.viewmodel

import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionsDataSource
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.ConversionRatesAndAllCurrenciesDataSyncUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.GetAllConversionsUseCase
import com.sameershelar.currencyconverterkmm.converter.presentation.CurrencyConversionEvents
import com.sameershelar.currencyconverterkmm.converter.presentation.CurrencyConversionState
import com.sameershelar.currencyconverterkmm.converter.presentation.mapper.toUIConvertedCurrency
import com.sameershelar.currencyconverterkmm.converter.presentation.mapper.toUiCurrencies
import com.sameershelar.currencyconverterkmm.core.domain.util.ConversionError
import com.sameershelar.currencyconverterkmm.core.domain.util.ConversionException
import com.sameershelar.currencyconverterkmm.core.domain.util.Resource
import com.sameershelar.currencyconverterkmm.core.domain.util.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyConversionViewModel(
    private val dataSyncUseCase: ConversionRatesAndAllCurrenciesDataSyncUseCase,
    private val getAllConversionsUseCase: GetAllConversionsUseCase,
    private val dataSource: ICurrencyConversionsDataSource,
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    init {
        onEvent(CurrencyConversionEvents.SyncData)
    }

    private val _state = MutableStateFlow(CurrencyConversionState())
    val state = combine(
        _state,
        dataSource.getAllCurrencies(),
        dataSource.getLatestConversionRates()
    ) { state, currencies, conversionRates ->
        if (state.allCurrencies != currencies.toUiCurrencies() ||
            state.conversionRate != conversionRates
        ) {
            _state.update {
                it.copy(
                    conversionRate = conversionRates
                )
            }
            state.copy(
                allCurrencies = currencies.toUiCurrencies(),
                conversionRate = conversionRates
            )
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CurrencyConversionState())
        .toCommonStateFlow()

    fun onEvent(event: CurrencyConversionEvents) {
        when (event) {
            is CurrencyConversionEvents.ChangeAmount -> {
                currencyConversionJob?.cancel()
                val newState = _state.updateAndGet {
                    it.copy(
                        fromAmount = event.amount
                    )
                }
                performCurrencyConversion(state = newState)
            }

            is CurrencyConversionEvents.ChooseFromCurrency -> {
                currencyConversionJob?.cancel()
                val newState = _state.updateAndGet {
                    it.copy(
                        isChoosingCurrencyCode = false,
                        fromCurrency = event.currency
                    )
                }
                performCurrencyConversion(state = newState)
            }

            CurrencyConversionEvents.OnErrorSeen -> {
                _state.update {
                    it.copy(
                        isConverting = false,
                        error = null
                    )
                }
            }

            CurrencyConversionEvents.OpenFromCurrencyDropDown -> {
                _state.update {
                    it.copy(isChoosingCurrencyCode = true)
                }
            }

            CurrencyConversionEvents.StopChoosingCurrency -> {
                _state.update {
                    it.copy(isChoosingCurrencyCode = false)
                }
            }

            CurrencyConversionEvents.SyncData -> {
                viewModelScope.launch(Dispatchers.Main) {
                    dataSyncUseCase()
                }
            }
        }
    }

    private var currencyConversionJob: Job? = null

    private fun performCurrencyConversion(state: CurrencyConversionState) {
        if (state.isConverting || state.fromAmount <= 0) {
            return
        }

        currencyConversionJob = viewModelScope.launch {

            _state.update { it.copy(isConverting = true) }

            if (state.conversionRate.isNotEmpty()) {
                val result = getAllConversionsUseCase(
                    conversionRate = state.conversionRate,
                    fromCurrencyCode = state.fromCurrency.currencyCode,
                    fromAmount = state.fromAmount
                )

                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isConverting = false,
                                allConversions = result.data?.map { convertedCurrency ->
                                    convertedCurrency.toUIConvertedCurrency()
                                }
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = (result.throwable as? ConversionException)?.error
                            )
                        }
                    }
                }
            } else {
                _state.update {
                    it.copy(
                        isConverting = false,
                        error = ConversionError.CONVERSION_RATE_UNAVAILABLE
                    )
                }
            }
        }
    }
}