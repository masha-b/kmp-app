//
//  AuthScreenView.swift
//  iosApp
//
//  Created by Бирюкова Мария on 16.01.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct AuthScreenView: View {
    
    @Binding var isPresented: Bool
    @EnvironmentObject var appState: AppState
    @State private var token: String = "sample-token"  // Replace with real authentication logic
    
    var body: some View {
        VStack {
            Text("Authorization Screen")
            Button("Login and Save Token") {
                appState.saveToken(token)
                appState.checkToken()
            }
        }
        .padding()
    }
}
