package kz.btokmyrza.anygram.feature.home.di

import com.arkivanov.decompose.ComponentContext
import kz.btokmyrza.anygram.feature.home.navigation.HomeRootComponent
import org.koin.dsl.module

val homeModule = module {

    factory { (
                  componentContext: ComponentContext,
              ) ->
        HomeRootComponent(
            componentContext = componentContext,
        )
    }
}
