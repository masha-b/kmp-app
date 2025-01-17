import SwiftUI
import KMPNativeCoroutinesAsync
import KMPObservableViewModelSwiftUI
import Shared

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
                    Label("Settings", systemImage: "gear")
                }
        }
        .environmentObject(appState)
        .fullScreenCover(isPresented: $appState.isShowingAuth) {
            AuthScreenView(isPresented: $appState.isShowingAuth).environmentObject(appState)
        }
    }
}

