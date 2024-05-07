//
//  ConversionAmountTextField.swift
//  iosApp
//
//  Created by Sameer Shelar on 08/06/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ConversionAmountTextField: View {
    @Binding var amountText: String
    
    var body: some View {
        TextField("Enter Amount to Convert", text: $amountText)
            .padding(10)
            .border(Color.lightGrey, width: 1)
            .keyboardType(.numberPad)
    }
}

struct ConversionAmountTextField_Previews: PreviewProvider {
    static var previews: some View {
        ConversionAmountTextField(
            amountText: Binding(
                get: { "" },
                set: { value in }
            )
        )
    }
    
}
