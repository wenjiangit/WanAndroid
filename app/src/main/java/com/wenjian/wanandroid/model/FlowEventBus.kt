package com.wenjian.wanandroid.model

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.wenjian.wanandroid.WanAndroidApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

/**
 * Description FlowEventBus
 *
 * Date 2023/1/7
 * @author wenjian@dayuwuxian.com
 */
object FlowEventBus {

    private val flowEvents = ConcurrentHashMap<String, MutableSharedFlow<Event>>()
    const val TAG = "FlowEventBus"


    fun post(event: Event, delayMillis: Long = 0) {
        WanAndroidApp.appMainScope.launch {
            delay(delayMillis)
            getEventFlow(event::class.java.simpleName).apply {
                Log.e(TAG, "emit: $event")
                emit(event)
            }
        }
    }

    fun getEventFlow(key: String): MutableSharedFlow<Event> {
        return flowEvents.getOrPut(key) { MutableSharedFlow() }
    }

    inline fun <reified T : Event> observe(
        lifecycleOwner: LifecycleOwner,
        minState: Lifecycle.State = Lifecycle.State.CREATED,
        crossinline receiver: (T) -> Unit
    ) {
        getEventFlow(T::class.java.simpleName)
            .flowWithLifecycle(lifecycleOwner.lifecycle, minState)
            .filterIsInstance<T>()
            .onEach {
                Log.e(TAG, "receive: $it")
            }
            .onEach { receiver(it) }
            .launchIn(WanAndroidApp.appMainScope)
    }

}