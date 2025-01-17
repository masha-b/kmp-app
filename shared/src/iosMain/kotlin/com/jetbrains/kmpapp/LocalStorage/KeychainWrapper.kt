package com.jetbrains.kmpapp.LocalStorage

import kotlinx.cinterop.*
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFTypeRefVar
import platform.Foundation.*
import platform.Security.*

object KeychainWrapper {

    fun saveToken(token: String, service: String = "com.example.app", account: String = "userToken"): Boolean {
        val tokenData = token.encodeToByteArray().toNSData()
        val attributes = NSDictionary.dictionaryWithObjectsAndKeys(
            listOf(kSecClass, kSecAttrService, kSecAttrAccount, kSecValueData),
            listOf(kSecClassGenericPassword, service, account, tokenData)
        )

        SecItemDelete(attributes.toCFDictionary()) // Удалить существующий элемент, если он есть
        val status = SecItemAdd(attributes.toCFDictionary(), null)
        return status == errSecSuccess
    }

    fun getToken(service: String = "com.example.app", account: String = "userToken"): String? {
        val query = NSDictionary.dictionaryWithObjectsAndKeys(
            listOf(kSecClass, kSecAttrService, kSecAttrAccount, kSecReturnData, kSecMatchLimit),
            listOf(kSecClassGenericPassword, service, account, true, kSecMatchLimitOne)
        )

        memScoped {
            val result = alloc<CFTypeRefVar>()
            val status = SecItemCopyMatching(query.toCFDictionary(), result.ptr)
            if (status == errSecSuccess) {
                val data = result.value as? NSData
                //return data?.toByteArray()?.decodeToString()
                return data?.string()
            }
        }
        return null
    }

    fun clearToken(service: String = "com.example.app", account: String = "userToken"): Boolean {
        val query = NSDictionary
            .dictionaryWithObjectsAndKeys(
            listOf(kSecClass, kSecAttrService, kSecAttrAccount),  // Значения
            listOf(kSecClassGenericPassword, service, account)       // Ключи
        )
        val status = SecItemDelete(query.toCFDictionary())
        return status == errSecSuccess
    }

    fun NSData.string(): String? = NSString.create(data = this, encoding = NSUTF8StringEncoding)?.toString()

    private fun ByteArray.toNSData(): NSData = this.usePinned {
        NSData.create(bytes = it.addressOf(0), length = this.size.toULong())
    }

//    private fun NSData.toByteArray(): ByteArray = ByteArray(this.length.toInt()).also {
//        this.getBytes(it.refTo(0), this.length)
//    }

    // Вспомогательная функция для преобразования Map в CFDictionaryRef
    private fun Map<*, *>.toCFDictionary(): CFDictionaryRef {
        val nsDictionary = NSDictionary.dictionaryWithObjectsAndKeys(
            this.values.toList(),
            this.keys.toList()
        )
        return objc_retain(nsDictionary.objcPtr()) as CFDictionaryRef
    }
}