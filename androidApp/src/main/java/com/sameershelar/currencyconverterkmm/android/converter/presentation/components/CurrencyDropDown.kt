package com.sameershelar.currencyconverterkmm.android.converter.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sameershelar.currencyconverterkmm.android.R
import com.sameershelar.currencyconverterkmm.converter.presentation.model.UICurrency

@Composable
fun CurrencyDropDown(
    currency: UICurrency,
    allCurrencies: List<UICurrency>,
    isOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelectCurrency: (UICurrency) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = onDismiss
        ) {
            allCurrencies.forEach { currency ->
                CurrencyDropDownItem(
                    currency = currency,
                    onClick = {
                        onSelectCurrency(currency)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currency.currencyName,
            )
            Icon(
                imageVector = if(isOpen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if(isOpen) {
                    stringResource(id = R.string.open)
                } else {
                    stringResource(id = R.string.close)
                },
                modifier = Modifier.size(30.dp)
            )
        }
    }
}