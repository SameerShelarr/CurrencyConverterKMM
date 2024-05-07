//
//  CurrencyConversionScreen.swift
//  iosApp
//
//  Created by Sameer Shelar on 07/06/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CurrencyConversionScreen: View {
    private var dataSyncUseCase: ConversionRatesAndAllCurrenciesDataSyncUseCase
    private var getAllConversionsUseCase: GetAllConversionsUseCase
    private var dataSource: ICurrencyConversionsDataSource
    
    @ObservedObject var viewModel: IOSCurrencyConversionViewModel
    
    init(
        dataSyncUseCase: ConversionRatesAndAllCurrenciesDataSyncUseCase,
        getAllConversionsUseCase: GetAllConversionsUseCase,
        dataSource: ICurrencyConversionsDataSource
    ) {
        self.dataSyncUseCase = dataSyncUseCase
        self.getAllConversionsUseCase = getAllConversionsUseCase
        self.dataSource = dataSource
        self.viewModel = IOSCurrencyConversionViewModel(
            dataSyncUseCase: dataSyncUseCase,
            getAllConversionsUseCase: getAllConversionsUseCase,
            dataSource: dataSource
        )
    }
    
    var body: some View {
        ZStack {
            List {
                ConversionAmountTextField(
                    amountText: Binding(
                        get: {
                            if viewModel.state.fromAmount == 0 {
                                return ""
                            } else {
                                return String(viewModel.state.fromAmount)
                            }
                        },
                        set: { text in
                            viewModel.onEvent(
                                event: CurrencyConversionEvents.ChangeAmount(
                                    amount: Int32(text) ?? 0
                                )
                            )
                        }
                    )
                )
                
                HStack(alignment: .center) {
                    CurrencyDropDown(
                        currency: viewModel.state.fromCurrency,
                        currencies: viewModel.state.allCurrencies,
                        isOpen: viewModel.state.isChoosingCurrencyCode,
                        selectCurrency: { currency in
                            viewModel.onEvent(
                                event: CurrencyConversionEvents.ChooseFromCurrency(
                                    currency: currency
                                )
                            )
                        }
                    )
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.white)
                
                if let allConversions = viewModel.state.allConversions {
                    if !allConversions.isEmpty {
                        Text("All Conversions")
                            .font(.title)
                            .bold()
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color.white)
                    }
                    
                    ForEach(allConversions, id: \.self.toCurrencyCode) { conversionItem in
                        CurrencyConversionItem(item: conversionItem)
                    }
                }
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
    }
}
