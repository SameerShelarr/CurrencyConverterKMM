package com.sameershelar.currencyconverterkmm.android.converter.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sameershelar.currencyconverterkmm.android.R
import com.sameershelar.currencyconverterkmm.core.presentation.Colors

@Composable
fun ConversionAmountTextField(
    conversionAmount: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color(Colors.LightGrey)
            )
            .padding(16.dp)
    ) {
        BasicTextField(
            value = conversionAmount,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )
        if (conversionAmount.isEmpty() && !isFocused) {
            Text(
                text = stringResource(
                    id = R.string.enter_amount_to_convert
                ),
            )
        }
    }
}