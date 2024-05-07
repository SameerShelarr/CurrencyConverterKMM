package com.sameershelar.currencyconverterkmm.android.converter.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sameershelar.currencyconverterkmm.android.R
import com.sameershelar.currencyconverterkmm.android.converter.presentation.components.ConversionAmountTextField
import com.sameershelar.currencyconverterkmm.android.converter.presentation.components.CurrencyConversionItem
import com.sameershelar.currencyconverterkmm.android.converter.presentation.components.CurrencyDropDown
import com.sameershelar.currencyconverterkmm.converter.presentation.CurrencyConversionEvents
import com.sameershelar.currencyconverterkmm.converter.presentation.CurrencyConversionState
import com.sameershelar.currencyconverterkmm.converter.presentation.model.UICurrency
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.DEFAULT_BASE_CURRENCY
import com.sameershelar.currencyconverterkmm.core.domain.util.ConversionError
import com.sameershelar.currencyconverterkmm.core.presentation.Colors

@Composable
fun CurrencyConversionScreen(
    state: CurrencyConversionState,
    onEvent: (CurrencyConversionEvents) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) {
        val message = when (state.error) {
            ConversionError.SERVICE_UNAVAILABLE,
            ConversionError.CLIENT_ERROR,
            ConversionError.SERVER_ERROR -> context.getString(R.string.server_error_please_try_again_later)

            ConversionError.UNKNOWN_ERROR -> context.getString(R.string.unknown_error_occurred)
            ConversionError.CONVERSION_RATE_UNAVAILABLE ->
                context.getString(R.string.conversion_rates_unavailable)

            null -> null
        }
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            onEvent(CurrencyConversionEvents.OnErrorSeen)
        }
    }

    Surface(
        color = Color(Colors.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ConversionAmountTextField(
                    conversionAmount = if (state.fromAmount == 0) "" else state.fromAmount.toString(),
                    onTextChange = {
                        onEvent(
                            CurrencyConversionEvents.ChangeAmount(
                                if (it.isEmpty() ||
                                    it.isBlank() ||
                                    it == "-" ||
                                    it == "," ||
                                    it == "."
                                ) {
                                    0
                                } else {
                                    it.filter { n -> n.isDigit() }.toInt()
                                }
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                CurrencyDropDown(
                    currency = state.fromCurrency,
                    allCurrencies = state.allCurrencies,
                    isOpen = state.isChoosingCurrencyCode,
                    onClick = {
                        onEvent(
                            CurrencyConversionEvents.OpenFromCurrencyDropDown
                        )
                    },
                    onDismiss = {
                        onEvent(
                            CurrencyConversionEvents.StopChoosingCurrency
                        )
                    },
                    onSelectCurrency = { selectedCurrency ->
                        onEvent(
                            CurrencyConversionEvents.ChooseFromCurrency(
                                currency = selectedCurrency
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            state.allConversions?.let { conversionsList ->

                item {
                    if (conversionsList.isNotEmpty()) {
                        Text(
                            text = stringResource(
                                id = R.string.all_conversions
                            ),
                            style = MaterialTheme.typography.h2
                        )
                    }
                }

                items(conversionsList) { item ->
                    CurrencyConversionItem(
                        item = item,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}