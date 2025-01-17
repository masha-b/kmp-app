package com.jetbrains.kmpapp.LocalStorage

import com.jetbrains.kmpapp.constants.CONST.AUTH_TOKEN
import com.jetbrains.kmpapp.di.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.liftric.kvault.KVault


class LocalStorage: Storage {
    val store: KVault
    init {
        store = KVault("ru.sitesoftenterprise.rosneft-brands")
    }

   override var authToken: String?
       get() = store.string(forKey = AUTH_TOKEN).orEmpty()
       set(value) {
           println("store AUTH_TOKEN: ${value.orEmpty()}")
           store.set(key = AUTH_TOKEN, stringValue = value.orEmpty())
           println("stored AUTH_TOKEN = ${authToken}")
           //field = value
       }
//       get() = KeychainWrapper.getToken().orEmpty()
//       set(value) {
//           KeychainWrapper.saveToken(value)
//       }



    @Throws(Exception::class)
    override fun getTokenFromPrefsAsFlow(): Flow<String> {
        try {
            return  flowOf("")
        } catch (e: Throwable) {
            throw e
        }
    }
}


