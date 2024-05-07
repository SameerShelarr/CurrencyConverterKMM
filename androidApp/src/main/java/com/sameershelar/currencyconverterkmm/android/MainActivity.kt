package com.sameershelar.currencyconverterkmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sameershelar.currencyconverterkmm.android.converter.presentation.AndroidCurrencyConversionViewModel
import com.sameershelar.currencyconverterkmm.android.converter.presentation.CurrencyConversionScreen
import com.sameershelar.currencyconverterkmm.android.converter.presentation.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: AndroidCurrencyConversionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val state by viewModel.state.collectAsState()
                CurrencyConversionScreen(
                    state = state,
                    onEvent = { event -> viewModel.onEvent(event) }
                )
            }
        }
    }
}
