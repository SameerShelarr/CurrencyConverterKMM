package com.sameershelar.currencyconverterkmm.android.converter.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sameershelar.currencyconverterkmm.converter.presentation.model.UIConvertedCurrency
import com.sameershelar.currencyconverterkmm.core.presentation.Colors

@Composable
fun CurrencyConversionItem(
    item: UIConvertedCurrency,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .border(width = 2.dp, color = Color(Colors.Black))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${item.fromCurrencyCode} ${item.fromCurrencyAmount}",
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${item.toCurrencyCode} ${item.toCurrencyAmount}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Medium
            )
        }
    }
}