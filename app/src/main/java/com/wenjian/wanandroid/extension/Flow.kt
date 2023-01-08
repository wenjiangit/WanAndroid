package com.wenjian.wanandroid.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Description Flow
 *
 * Date 2023/1/8
 * @author wenjian@dayuwuxian.com
 */

/**
 * 用于需要持续观察的场景，热流
 */
fun <T> Flow<T?>.safeObserve(
    lifecycleOwner: LifecycleOwner,
    minState: Lifecycle.State = Lifecycle.State.STARTED,
    success: (T) -> Unit
) {
    this.onEach {
        it?.run(success)
    }.flowWithLifecycle(lifecycleOwner.lifecycle, minState)
        .launchIn(lifecycleOwner.lifecycleScope)
}

/**
 * 用于一次性的场景，冷流
 */
fun <T> Flow<T?>.safeCollect(
    lifecycleOwner: LifecycleOwner,
    success: (T) -> Unit
) {
    this.onEach {
        it?.run(success)
    }.launchIn(lifecycleOwner.lifecycleScope)
}