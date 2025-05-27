package kz.btokmyrza.anygram.feature.auth.navigation.di

import com.arkivanov.decompose.ComponentContext
import kz.btokmyrza.anygram.feature.auth.navigation.AuthRootComponent
import org.koin.dsl.module

val authNavigationModule = module {

    factory { (
                  componentContext: ComponentContext,
                  onNavigateToHome: () -> Unit,
              ) ->
        AuthRootComponent(
            componentContext = componentContext,
            onNavigateToHome = onNavigateToHome,
        )
    }
}
