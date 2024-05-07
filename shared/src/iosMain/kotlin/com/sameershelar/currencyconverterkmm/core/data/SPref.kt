package com.sameershelar.currencyconverterkmm.core.data

import platform.Foundation.NSUserDefaults
import platform.darwin.NSInteger
import platform.darwin.NSObject

actual typealias SPref = NSObject

actual fun SPref.getLong(key: String): Long {
    return NSUserDefaults.standardUserDefaults.integerForKey(key)
}

actual fun SPref.setLong(key: String, value: NSInteger){
    NSUserDefaults.standardUserDefaults.setInteger(value, key)
}