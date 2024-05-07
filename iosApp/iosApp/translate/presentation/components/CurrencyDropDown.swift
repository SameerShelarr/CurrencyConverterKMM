//
//  CurrencyDropDown.swift
//  iosApp
//
//  Created by Sameer Shelar on 07/06/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CurrencyDropDown: View {
    var currency: UICurrency
    var currencies: [UICurrency]
    var isOpen: Bool
    var selectCurrency: (UICurrency) -> Void
    var body: some View {
        Menu {
            VStack {
                ForEach(currencies, id: \.self.currencyCode) { it in
                    CurrencyDropDownItem(
                        currency: it,
                        onClick: {
                            selectCurrency(it)
                        }
                    )
                }
            }
        } label: {
            HStack {
                Text(currency.currencyName)
                    .foregroundColor(Color.textBlack)
                Image(systemName: isOpen ? "chevron.up" : "chevron.down")
                    .foregroundColor(Color.textBlack)
            }
        }
    }
}

struct CurrencyDropDown_Previews: PreviewProvider {
    static var previews: some View {
        let currencies = [
            UICurrency(
                currencyCode: "INR",
                currencyName: "Indian Rupee"
            ),
            UICurrency(
                currencyCode: "INR",
                currencyName: "Indian Rupee"
            ),
            UICurrency(
                currencyCode: "INR",
                currencyName: "Indian Rupee"
            ),
            UICurrency(
                currencyCode: "INR",
                currencyName: "Indian Rupee"
            ),
            UICurrency(
                currencyCode: "INR",
                currencyName: "Indian Rupee"
            )
        ]
        
        CurrencyDropDown(
            currency: UICurrency(currencyCode: "USD", currencyName: "United States Dollar"),
            currencies: currencies,
            isOpen: false,
            selectCurrency: {currency in }
        )
    }
}
