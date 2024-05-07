package com.sameershelar.currencyconverterkmm.android.converter.presentation.components

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sameershelar.currencyconverterkmm.converter.presentation.model.UICurrency

@Composable
fun CurrencyDropDownItem(
    currency: UICurrency,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = currency.currencyName
        )
    }
}