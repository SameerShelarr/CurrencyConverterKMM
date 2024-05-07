//
//  CurrencyConversionItem.swift
//  iosApp
//
//  Created by Sameer Shelar on 08/06/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CurrencyConversionItem: View {
    let item: UIConvertedCurrency
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text("\(item.fromCurrencyCode) \(item.fromCurrencyAmount)")
                Text("\(item.toCurrencyCode) \(item.toCurrencyAmount)")
                    .bold()
            }
            Spacer()
        }
        .padding(10)
    }
}

struct CurrencyConversionItem_Previews: PreviewProvider {
    static var previews: some View {
        CurrencyConversionItem(
            item: UIConvertedCurrency(
                fromCurrencyCode: "USD",
                fromCurrencyAmount: 1,
                toCurrencyCode: "INR",
                toCurrencyAmount: 82.53
            )
        )
    }
}
