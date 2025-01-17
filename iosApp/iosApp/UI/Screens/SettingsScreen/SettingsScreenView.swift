//
//  SettingsScreenView.swift
//  iosApp
//
//  Created by Бирюкова Мария on 16.01.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct SettingsScreenView: View {
    
    var body: some View {
        Text("Token: " + (Dependencies().localStorage.authToken ?? ""))
        //Text("Token: " + LocalStorage().authToken)
    }
}

#Preview {
    SettingsScreenView()
}
