package kz.btokmyrza.anygram

import android.app.Application
import kz.btokmyrza.anygram.di.appModule
import kz.btokmyrza.anygram.feature.auth.common.di.authCommonModule
import kz.btokmyrza.anygram.feature.auth.navigation.di.authNavigationModule
import kz.btokmyrza.anygram.feature.auth.start.di.authStartModule
import kz.btokmyrza.anygram.feature.chats.di.chatsModule
import kz.btokmyrza.anygram.feature.country.chooser.di.countryChooserModule
import kz.btokmyrza.anygram.feature.home.di.homeModule
import kz.btokmyrza.anygram.feature.otp.di.otpModule
import kz.btokmyrza.anygram.feature.phone.number.di.phoneNumberModule
import kz.btokmyrza.library.core.data.di.coreDataModule
import kz.btokmyrza.library.core.domain.di.coreDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AnygramApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AnygramApplication)
            modules(
                appModule,
                authCommonModule,
                authNavigationModule,
                authStartModule,
                phoneNumberModule,
                countryChooserModule,
                otpModule,
                homeModule,
                chatsModule,
                coreDataModule,
                coreDomainModule,
            )
        }
    }
}
