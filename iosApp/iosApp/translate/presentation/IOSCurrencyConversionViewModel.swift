//
//  IOSCurrencyConversionViewModel.swift
//  iosApp
//
//  Created by Sameer Shelar on 07/06/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension CurrencyConversionScreen {
    @MainActor class IOSCurrencyConversionViewModel : ObservableObject {
        private var dataSyncUseCase: ConversionRatesAndAllCurrenciesDataSyncUseCase
        private var getAllConversionsUseCase: GetAllConversionsUseCase
        private var dataSource: ICurrencyConversionsDataSource
        
        private let viewModel: CurrencyConversionViewModel
        
        private var handle: DisposableHandle?
        
        @Published var state: CurrencyConversionState = CurrencyConversionState(
            fromAmount: 0,
            isConverting: false,
            fromCurrency: UICurrency(
                currencyCode: "USD",
                currencyName: "United States Dollar"
            ),
            isChoosingCurrencyCode: false,
            error: nil,
            conversionRate: [],
            allCurrencies: [],
            allConversions: nil
        )
        
        init(
            dataSyncUseCase: ConversionRatesAndAllCurrenciesDataSyncUseCase,
            getAllConversionsUseCase: GetAllConversionsUseCase,
            dataSource: ICurrencyConversionsDataSource
        ) {
            self.dataSyncUseCase = dataSyncUseCase
            self.getAllConversionsUseCase = getAllConversionsUseCase
            self.dataSource = dataSource
            self.viewModel = CurrencyConversionViewModel(
                dataSyncUseCase: dataSyncUseCase,
                getAllConversionsUseCase: getAllConversionsUseCase,
                dataSource: dataSource,
                coroutineScope: nil
            )
        }
        
        func onEvent(event: CurrencyConversionEvents) {
            self.viewModel.onEvent(event: event)
        }
        
        func startObserving() {
            handle = viewModel.state.subscribe(
                onCollect: { state in
                    if let state =  state {
                        self.state = state
                    }
                }
            )
        }
        
        func dispose() {
            handle?.dispose()
        }
    }
}
