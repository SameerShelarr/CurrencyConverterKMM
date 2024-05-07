package com.sameershelar.currencyconverterkmm.converter.data.local

import com.sameershelar.currencyconverterkmm.ConversionsDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(ConversionsDatabase.Schema,  "conversions.db")
    }
}
