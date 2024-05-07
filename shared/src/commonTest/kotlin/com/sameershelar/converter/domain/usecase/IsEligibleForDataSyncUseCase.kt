package com.sameershelar.converter.domain.usecase

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.IsEligibleForDataSyncUseCase
import kotlin.test.BeforeTest
import kotlin.test.Test

class IsEligibleForDataSyncUseCase {

    private lateinit var useCase: IsEligibleForDataSyncUseCase

    @BeforeTest
    fun setup() {
        useCase = IsEligibleForDataSyncUseCase()
    }

    @Test
    fun `When 30 minutes are elapsed from last sync it is eligible for data sync`() {
        val currentTime = 3900000L
        val lastSyncTime = 2000000L

        assertThat(
            useCase.invoke(
                lastSyncTimeStamp = lastSyncTime,
                currentTimeStamp = currentTime,
            )
        ).isTrue()
    }

    @Test
    fun `When 30 minutes are not elapsed from last sync it is not eligible for data sync`() {
        val currentTime = 3700000L
        val lastSyncTime = 2000000L

        assertThat(
            useCase.invoke(
                lastSyncTimeStamp = lastSyncTime,
                currentTimeStamp = currentTime,
            )
        ).isFalse()
    }
}