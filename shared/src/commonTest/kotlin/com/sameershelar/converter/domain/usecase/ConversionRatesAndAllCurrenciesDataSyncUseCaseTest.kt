package com.sameershelar.converter.domain.usecase

import com.sameershelar.currencyconverterkmm.converter.data.remote.model.ConversionRatesDTO
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionClient
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionLocalStorage
import com.sameershelar.currencyconverterkmm.converter.domain.ICurrencyConversionsDataSource
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.ConversionRatesAndAllCurrenciesDataSyncUseCase
import com.sameershelar.currencyconverterkmm.converter.domain.usecase.IsEligibleForDataSyncUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class ConversionRatesAndAllCurrenciesDataSyncUseCaseTest {

    private lateinit var fakeClient: ICurrencyConversionClient
    private lateinit var fakeDataSource: ICurrencyConversionsDataSource
    private lateinit var fakeLocalStorage: ICurrencyConversionLocalStorage
    private lateinit var isEligibleUseCase: IsEligibleForDataSyncUseCase

    private lateinit var useCase: ConversionRatesAndAllCurrenciesDataSyncUseCase

    @BeforeTest
    fun setup() {
        fakeClient = mockk(relaxed = true)
        fakeDataSource = mockk(relaxed = true)
        fakeLocalStorage = mockk(relaxed = true)
        isEligibleUseCase = mockk(relaxed = true)

        useCase = ConversionRatesAndAllCurrenciesDataSyncUseCase(
            currencyConversionClient = fakeClient,
            currencyConversionsDataSource = fakeDataSource,
            localStorage = fakeLocalStorage,
            isEligibleForDataSyncUseCase = isEligibleUseCase
        )
    }

    @Test
    fun `Set the new last sync time when data is synced`() = runBlocking {
        // Eligible for data sync.
        every { fakeLocalStorage.getLastDataSyncTimeStamp() } returns 0
        every { isEligibleUseCase.invoke(any(), any()) } returns true

        useCase.invoke()

        coVerify(exactly = 1) { fakeLocalStorage.setLastDataSyncTimeStamp(any()) }
    }

    @Test
    fun `Clear old conversion rates data before inserting the newly fetched data`() = runBlocking {
        // Eligible for data sync.
        every { fakeLocalStorage.getLastDataSyncTimeStamp() } returns 0
        every { isEligibleUseCase.invoke(any(), any()) } returns true

        coEvery { fakeClient.getLatestConversionRates(any()) } returns ConversionRatesDTO(
            base = "",
            disclaimer = "",
            license = "",
            rates = emptyMap(),
            timestamp = 0
        )

        useCase.invoke()

        coVerify(exactly = 1) { fakeDataSource.clearOldConversionRates() }
    }

    @Test
    fun `Clear old currency data before inserting the newly fetched data1`() = runBlocking {
        // Eligible for data sync.
        every { fakeLocalStorage.getLastDataSyncTimeStamp() } returns 0
        every { isEligibleUseCase.invoke(any(), any()) } returns true

        coEvery { fakeClient.getAllCurrencies() } returns emptyMap()

        useCase.invoke()

        coVerify(exactly = 1) { fakeDataSource.clearOldCurrencies() }
    }

    @Test
    fun `When not eligible for data sync don't sync the data`() = runBlocking {
        // Not eligible for data sync.
        every { fakeLocalStorage.getLastDataSyncTimeStamp() } returns 0
        every { isEligibleUseCase.invoke(any(), any()) } returns false

        useCase.invoke()

        // All the methods that do data sync.
        coVerify(exactly = 0) { fakeClient.getAllCurrencies() }
        coVerify(exactly = 0) { fakeDataSource.insertCurrency(any()) }
        coVerify(exactly = 0) { fakeDataSource.clearOldCurrencies() }
        coVerify(exactly = 0) { fakeDataSource.getLatestConversionRates() }
        coVerify(exactly = 0) { fakeDataSource.insertConversionRates(any()) }
        coVerify(exactly = 0) { fakeDataSource.clearOldConversionRates() }
        verify(exactly = 0) { fakeLocalStorage.setLastDataSyncTimeStamp(any()) }
    }
}