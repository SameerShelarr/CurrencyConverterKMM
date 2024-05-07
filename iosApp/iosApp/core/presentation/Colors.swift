//
//  Colors.swift
//  iosApp
//
//  Created by Sameer Shelar on 06/06/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

extension Color {
    init(hex: Int64, alpha: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255,
            green: Double((hex >> 08) & 0xff) / 255,
            blue: Double((hex >> 00) & 0xff) / 255,
            opacity: alpha
        )
    }
    
    private static let colors = Colors()
    static let white = Color(hex: colors.White)
    static let lightGrey = Color(hex: colors.LightGrey)
    static let textBlack = Color(hex: colors.Black)
}

private extension Color {
    init(light: Self, dark: Self) {
        self.init(uiColor: UIColor(light: UIColor(light), dark: UIColor(dark)))
    }
}

private extension UIColor {
    convenience init(light: UIColor, dark: UIColor) {
        self.init { traits in
            switch traits.userInterfaceStyle {
            case .light, .unspecified:
                return light
            case .dark:
                return dark
            @unknown default:
                return light
            }
        }
    }
}
