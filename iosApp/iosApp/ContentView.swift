import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()
    private let appModule = AppModule()

	var body: some View {
		CurrencyConversionScreen(
            dataSyncUseCase: appModule.dataSyncUseCase,
            getAllConversionsUseCase: appModule.getAllConversionsUseCase,
            dataSource: appModule.dataSource
        )
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
