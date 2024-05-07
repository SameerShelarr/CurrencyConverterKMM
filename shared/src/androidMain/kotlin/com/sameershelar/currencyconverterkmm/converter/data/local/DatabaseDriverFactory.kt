package com.sameershelar.currencyconverterkmm.converter.data.local

import android.content.Context
import com.sameershelar.currencyconverterkmm.ConversionsDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(ConversionsDatabase.Schema, context, "conversions.db")
    }
}
