package com.sameershelar.currencyconverterkmm.converter.data.remote

import io.ktor.client.HttpClient

expect class HttpClientFactory {
    fun create(): HttpClient
}