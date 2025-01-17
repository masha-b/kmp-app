//
//  KeychainHelper.swift
//  iosApp
//
//  Created by Бирюкова Мария on 17.01.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import Security

class KeychainHelper {
    static let shared = KeychainHelper()
    
    private let service = "ru.sitesoftenterprise.rosneft-brands"
    private let group: String? = nil
    private let account = "authToken"
    
    func saveToken(_ token: String) {
        guard let data = token.data(using: .utf8) else { return }
        let query: [String: Any] = [kSecValueData as String: data].keychainQuery(account: account, service: service)
        
        SecItemDelete(query as CFDictionary) // Delete any existing token
        SecItemAdd(query as CFDictionary, nil)
    }
    
    func getToken() -> String? {
        let query: [String: Any] = [kSecReturnData as String: true].keychainQuery(account: account, service: service)
        
        var item: CFTypeRef?
        if SecItemCopyMatching(query as CFDictionary, &item) == errSecSuccess {
            if let data = item as? Data {
                return String(data: data, encoding: .utf8)
            }
        }
        return nil
    }
    
    func deleteToken() {
        let query: [String: Any] = [:].keychainQuery(account: account, service: service)
        
        SecItemDelete(query as CFDictionary)
    }
    
}

extension Dictionary where Key == String, Value == Any {
    func keychainQuery(account: String, service: String, group: String? = nil) -> [String: Any] {
        var mutable = self
        mutable[kSecClass as String] = kSecClassGenericPassword
        mutable[kSecAttrService as String] = service
        mutable[kSecAttrAccount as String] = account
        
        guard let group else { return mutable }
        mutable[kSecAttrAccessGroup as String] = group
        return mutable
        
    }
}
