package kz.btokmyrza.library.core.data.datasource

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi

class TdlibDataSource {

    private val _updates = MutableSharedFlow<TdApi.Object>(
        replay = 1,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )
    val updates: SharedFlow<TdApi.Object> = _updates.asSharedFlow()

    private val updateHandler = object : Client.ResultHandler {

        override fun onResult(result: TdApi.Object) {
            _updates.tryEmit(result)
        }
    }
    private val updateExceptionHandler: Client.ExceptionHandler? = null
    private val defaultExceptionHandler: Client.ExceptionHandler? = null

    private val client = Client.create(
        updateHandler,
        updateExceptionHandler,
        defaultExceptionHandler,
    )

    init {
        client.send(TdApi.SetLogVerbosityLevel(5), null)
    }

    fun sendAsync(request: TdApi.Function<out TdApi.Object>): Flow<TdApi.Object> =
        callbackFlow {
            client.send(request) { result ->
                trySend(result)
                close()
            }

            awaitClose()
        }
}