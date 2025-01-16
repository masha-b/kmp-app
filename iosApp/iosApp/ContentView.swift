import SwiftUI
import KMPNativeCoroutinesAsync
import KMPObservableViewModelSwiftUI
import Shared

class AppState: ObservableObject {
    @Published var isShowingAuth: Bool = false
}

struct ContentView: View {
    
    @StateObject
    private var appState = AppState()
    
    var body: some View {
        
        TabView {
            HomeScreenView()
                .tabItem {
                    Label("Home", systemImage: "house")
                }
            SettingsScreenView()
                .tabItem {
                    Label("Search", systemImage: "settings")
                }
        }
        .environmentObject(appState)
        .fullScreenCover(isPresented: $appState.isShowingAuth) {
            AuthScreenView(isPresented: $appState.isShowingAuth)
        }
    }
}

