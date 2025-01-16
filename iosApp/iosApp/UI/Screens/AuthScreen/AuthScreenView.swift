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
        
        var body: some View {
            VStack {
                Text("Authorization Screen")
                Button("Dismiss") {
                    isPresented = false
                }
            }
            .padding()
        }
}
