package com.sameershelar.currencyconverterkmm.core.domain.util

enum class ConversionError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR,
    CONVERSION_RATE_UNAVAILABLE
}

class ConversionException(val error: ConversionError): Exception(
    when (error) {
        ConversionError.SERVICE_UNAVAILABLE,
        ConversionError.CLIENT_ERROR,
        ConversionError.SERVER_ERROR,
        ConversionError.UNKNOWN_ERROR -> {
            "An error occurred when fetching latest conversion rates: $error"
        }
        ConversionError.CONVERSION_RATE_UNAVAILABLE -> {
            "Conversion rates unavailable, Please try again later!"
        }
    }
)