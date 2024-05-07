package com.sameershelar.currencyconverterkmm.converter.data.remote

import com.sameershelar.currencyconverterkmm.converter.data.remote.model.ConversionRatesDTO
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionClient
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.ALL_CURRENCIES_END_POINT
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.APP_ID
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.DEFAULT_BASE_CURRENCY
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.BASE_URL
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.LATEST_EXCHANGE_RATE_END_POINT
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.PARAM_APP_ID_KEY
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.PARAM_BASE_KEY
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.PARAM_PRETTY_PRINT_KEY
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.PARAM_SHOW_ALTERNATIVE_KEY
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.PARAM_SHOW_INACTIVE_KEY
import com.sameershelar.currencyconverterkmm.core.domain.util.Constants.PARAM_SYMBOLS_KEY
import com.sameershelar.currencyconverterkmm.core.domain.util.ConversionError
import com.sameershelar.currencyconverterkmm.core.domain.util.ConversionException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.utils.io.errors.IOException

class KtorCurrencyConversionClient(
    private val httpClient: HttpClient
) : ICurrencyConversionClient {

    override suspend fun getLatestConversionRates(currencies: String): ConversionRatesDTO {

        val result = try {
            httpClient.get {
                url(
                    URLBuilder().apply {
                        takeFrom(BASE_URL)
                        encodedPath = LATEST_EXCHANGE_RATE_END_POINT
                        parameters.append(PARAM_APP_ID_KEY, APP_ID)
                        parameters.append(PARAM_BASE_KEY, DEFAULT_BASE_CURRENCY)
                        parameters.append(PARAM_SYMBOLS_KEY, currencies)
                        parameters.append(PARAM_PRETTY_PRINT_KEY, false.toString())
                        parameters.append(PARAM_SHOW_ALTERNATIVE_KEY, false.toString())
                    }.buildString()
                )
                contentType(ContentType.Application.Json)
            }
        } catch (e: IOException) {
            throw ConversionException(ConversionError.SERVICE_UNAVAILABLE)
        }

        return processResponse(result)
    }

    override suspend fun getAllCurrencies(): Map<String, String> {
        val result = try {
            httpClient.get {
                url(
                    URLBuilder().apply {
                        takeFrom(BASE_URL)
                        encodedPath = ALL_CURRENCIES_END_POINT
                        parameters.append(PARAM_PRETTY_PRINT_KEY, false.toString())
                        parameters.append(PARAM_SHOW_ALTERNATIVE_KEY, false.toString())
                        parameters.append(PARAM_SHOW_INACTIVE_KEY, false.toString())
                        parameters.append(PARAM_APP_ID_KEY, APP_ID)
                    }.buildString()
                )
                contentType(ContentType.Application.Json)
            }
        } catch (e: IOException) {
            throw ConversionException(ConversionError.SERVICE_UNAVAILABLE)
        }

        return processResponse(result)
    }

    private suspend inline fun <reified T> processResponse(result: HttpResponse): T {
        when (result.status.value) {
            in 200..299 -> Unit
            500 -> throw ConversionException(ConversionError.SERVER_ERROR)
            in 400..499 -> throw ConversionException(ConversionError.CLIENT_ERROR)
            else -> throw ConversionException(ConversionError.UNKNOWN_ERROR)
        }

        return try {
            result.body()
        } catch (e: Exception) {
            throw ConversionException(ConversionError.SERVER_ERROR)
        }
    }
}