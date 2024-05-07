//
//  CurrencyDropDownItem.swift
//  iosApp
//
//  Created by Sameer Shelar on 07/06/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CurrencyDropDownItem: View {
    var currency: UICurrency
    var onClick: () -> Void
    var body: some View {
        Button(action: onClick) {
            Text(currency.currencyName)
                .foregroundColor(.textBlack)
        }
    }
}

struct CurrencyDropDownItem_Previews: PreviewProvider {
    static var previews: some View {
        CurrencyDropDownItem(
            currency: UICurrency(
                currencyCode: "USD",
                currencyName: "Unites States Dollar"
            ),
            onClick: {}
        )
    }
}
