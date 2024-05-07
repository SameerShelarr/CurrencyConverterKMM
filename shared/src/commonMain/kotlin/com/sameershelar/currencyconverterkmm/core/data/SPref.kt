package com.sameershelar.currencyconverterkmm.core.data

expect class SPref

expect fun SPref.getLong(key: String) : Long
expect fun SPref.setLong(key: String, value: Long)