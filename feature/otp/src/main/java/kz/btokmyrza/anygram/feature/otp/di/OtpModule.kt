package kz.btokmyrza.anygram.feature.otp.di

import com.arkivanov.decompose.ComponentContext
import kz.btokmyrza.anygram.feature.otp.domain.use.cases.SendOtpUseCase
import kz.btokmyrza.anygram.feature.otp.presentation.component.DefaultOtpComponent
import kz.btokmyrza.anygram.feature.otp.presentation.component.OtpComponent
import org.koin.dsl.module

val otpModule = module {

    factory {
        SendOtpUseCase(
            authRepository = get(),
        )
    }

    factory<OtpComponent> { (
                                componentContext: ComponentContext,
                                onCodeEnterFinished: () -> Unit,
                                onNavigateBack: () -> Unit,
                            ) ->
        DefaultOtpComponent(
            componentContext = componentContext,
            onNavigateToHome = onCodeEnterFinished,
            onNavigateBack = onNavigateBack,
            observeAuthUpdatesUseCase = get(),
            sendOtpUseCase = get(),
        )
    }
}
