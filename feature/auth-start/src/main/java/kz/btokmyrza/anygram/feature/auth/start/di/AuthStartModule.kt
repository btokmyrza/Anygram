package kz.btokmyrza.anygram.feature.auth.start.di

import android.os.Build
import com.arkivanov.decompose.ComponentContext
import kz.btokmyrza.anygram.feature.auth.start.R
import kz.btokmyrza.anygram.feature.auth.start.domain.model.AuthParameters
import kz.btokmyrza.anygram.feature.auth.start.domain.use.cases.StartAuthUseCase
import kz.btokmyrza.anygram.feature.auth.start.presentation.component.AuthStartComponent
import kz.btokmyrza.anygram.feature.auth.start.presentation.component.DefaultAuthStartComponent
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.Locale

val authStartModule = module {

    factory {
        AuthParameters(
            useTestDc = true,
            databaseDirectory = androidContext().filesDir.absolutePath,
            filesDirectory = androidContext().filesDir.absolutePath,
            databaseEncryptionKey = "",
            useFileDatabase = true,
            useChatInfoDatabase = true,
            useMessageDatabase = true,
            useSecretChats = false,
            apiId = androidContext().resources.getInteger(R.integer.telegram_api_id),
            apiHash = androidContext().getString(R.string.telegram_api_hash),
            systemLanguageCode = Locale.getDefault().language,
            deviceModel = Build.MODEL,
            systemVersion = Build.VERSION.RELEASE,
            applicationVersion = "0.1",
        )
    }

    factory {
        StartAuthUseCase(
            authRepository = get(),
            authParameters = get(),
        )
    }

    factory<AuthStartComponent> { (
                                      componentContext: ComponentContext,
                                      onNavigateToPhoneNumber: () -> Unit,
                                  ) ->
        DefaultAuthStartComponent(
            componentContext = componentContext,
            onNavigateToPhoneNumber = onNavigateToPhoneNumber,
            observeThemeModeUseCase = get(),
            setDarkModeUseCase = get(),
            observeAuthUpdatesUseCase = get(),
            startAuthUseCase = get(),
        )
    }
}
