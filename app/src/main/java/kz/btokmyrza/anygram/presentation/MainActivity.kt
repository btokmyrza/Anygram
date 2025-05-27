package kz.btokmyrza.anygram.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.defaultComponentContext
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val rootComponentContext = defaultComponentContext()
        val appRootComponent: AppRootComponent = get {
            parametersOf(rootComponentContext)
        }

        setContent {
            val isDarkModeEnabled by appRootComponent.isDarkModeEnabled.collectAsState()

            AnygramTheme(darkTheme = isDarkModeEnabled) {
                AppRootScreen(component = appRootComponent)
            }
        }
    }
}
