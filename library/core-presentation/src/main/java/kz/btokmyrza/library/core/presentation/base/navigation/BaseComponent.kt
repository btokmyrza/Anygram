package kz.btokmyrza.library.core.presentation.base.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BaseComponent(
    componentContext: ComponentContext,
) : CoroutineScope, ComponentContext by componentContext {

    private val supervisorJob by lazy { SupervisorJob() }

    override val coroutineContext: CoroutineContext
        get() = supervisorJob + Dispatchers.Main.immediate

    init {
        lifecycle.doOnDestroy {
            supervisorJob.cancelChildren()
        }
    }
}
