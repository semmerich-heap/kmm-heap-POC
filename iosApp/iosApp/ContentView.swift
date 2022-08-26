import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
	var body: some View {
        NavigationView {
            listView()
                .navigationBarTitle("Heap Events")
                .navigationBarItems(leading: Button("Add") {
                    self.viewModel.addEvent()
                }, trailing: Button("Send") {
                    self.viewModel.loadEvents()
                })
        }
	}
    private func listView() -> AnyView {
        switch viewModel.events {
        case .loading:
            return AnyView(Text("Loading ...").multilineTextAlignment(.center))
        case .result(let events):
            return AnyView(List(events) { event in EventRow(event: event)})
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
}
extension ContentView {
    enum LoadableEvents {
        case loading
        case result([Event])
        case error(String)
    }
    class ViewModel: ObservableObject {
        let sdk: HeapSDK
        @Published var events = LoadableEvents.loading
        
        init(sdk: HeapSDK) {
            self.sdk = sdk
            self.loadEvents()
        }
        func addEvent() {
            sdk.sendEvent(id: Int.random(in: 1..<255).description, completionHandler: { error in
                self.events = .error(error?.localizedDescription ?? "error")
            })
        }
        func loadEvents() {
            self.events = .loading
            sdk.getEvents(
                completionHandler: { events, error in
                    if let events = events {
                        self.events = .result(events);
                    } else {
                        self.events = .error(error?.localizedDescription ?? "error")
                    }
                 })


        }
    }
}

extension Event: Identifiable { }
