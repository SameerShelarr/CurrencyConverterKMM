package com.sameershelar.currencyconverterkmm.converter.domain.usecase

class IsEligibleForDataSyncUseCase {
    operator fun invoke(
        lastSyncTimeStamp: Long,
        currentTimeStamp: Long
    ): Boolean {
        val diff = 1800000 // 30 minutes
        return currentTimeStamp - lastSyncTimeStamp > diff
    }
}