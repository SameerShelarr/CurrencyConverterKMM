package com.sameershelar.currencyconverterkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform