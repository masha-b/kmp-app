import SwiftUI
import Shared

class AppState: ObservableObject {
    @Published var isShowingAuth: Bool = false
    init() {
        deleteToken()
        checkToken()
    }
    
    func checkToken() {
        if Dependencies().localStorage.authToken == nil {
            isShowingAuth = true
        }
    }
    
    func saveToken(_ token: String) {
        Dependencies().localStorage.authToken = token
        isShowingAuth = false
        print("token = \(KeychainHelper.shared.getToken() ?? "nil")")
    }
    
    func deleteToken() {
        Dependencies().localStorage.authToken = nil
        isShowingAuth = true
    }
}
