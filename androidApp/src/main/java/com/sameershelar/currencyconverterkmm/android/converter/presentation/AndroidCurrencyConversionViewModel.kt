package com.sameershelar.currencyconverterkmm.android.converter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionsDataSource
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.ConversionRatesAndAllCurrenciesDataSyncUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.GetAllConversionsUseCase
import com.sameershelar.currencyconverterkmm.converter.presentation.CurrencyConversionEvents
import com.sameershelar.currencyconverterkmm.converter.presentation.viewmodel.CurrencyConversionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidCurrencyConversionViewModel @Inject constructor(
    private val dataSyncUseCase: ConversionRatesAndAllCurrenciesDataSyncUseCase,
    private val getAllConversionsUseCase: GetAllConversionsUseCase,
    private val dataSource: ICurrencyConversionsDataSource,
) : ViewModel() {
    private val viewModel by lazy {
        CurrencyConversionViewModel(
            dataSyncUseCase = dataSyncUseCase,
            getAllConversionsUseCase = getAllConversionsUseCase,
            dataSource = dataSource,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: CurrencyConversionEvents) {
        viewModel.onEvent(event)
    }
}