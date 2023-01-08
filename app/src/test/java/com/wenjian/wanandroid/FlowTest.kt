package com.wenjian.wanandroid

import com.wenjian.wanandroid.net.Resp
import com.wenjian.wanandroid.net.getOrThrow
import com.wenjian.wanandroid.net.onFail
import com.wenjian.wanandroid.net.onSuccess
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Description FlowTest
 *
 * Date 2023/1/7
 * @author wenjian@dayuwuxian.com
 */
class FlowTest {

    @Test
    fun test_catch() = runBlocking {
        flow<Resp<Int>> {
            throw RuntimeException("异常了")
        }.catch {
            println("catch: ${it.message}")
        }.onSuccess {
            println("onSuccess: $it")
        }.onFail {
            println("onFail: ${it.errorMsg}")
        }.collect()
    }

    @Test
    fun test_catch_emit() = runBlocking {
        flow<Resp<Int>> {
            throw RuntimeException("异常了")
        }.catch {
            emit(Resp(data = 1001, exception = it))
            println("catch: ${it.message}")
        }.onSuccess {
            println("onSuccess: $it")
        }.onFail {
            println("onFail: ${it.errorMsg}")
        }.collect()
    }

    @Test
    fun test_catch_throw_api_exception() = runBlocking {
        flow<Resp<Int>> {
            emit(Resp(1, 10001, "参数异常"))
        }.map { Resp(it.getOrThrow()) }
            .catch {
                emit(Resp(data = 1001, exception = it))
                println("catch: ${it.message}")
            }.onSuccess {
                println("onSuccess: $it")
            }.onFail {
                println("onFail: ${it.errorMsg}")
            }.collect()
    }

    @Test
    fun test_catch_throw_api_exception_1() = runBlocking {
        flow<Resp<Int>> {
            emit(Resp(1, 10001, "参数异常"))
        }
            .catch {
                emit(Resp(data = 1001, exception = it))
                println("catch: ${it.message}")
            }.map { Resp(it.getOrThrow()) }
            .onSuccess {
                println("onSuccess: $it")
            }.onFail {
                println("onFail: ${it.errorMsg}")
            }.collect()
    }

    @Test
    fun test_catch_throw_api_exception_2() = runBlocking {
        flow<Resp<Int>> {
            emit(Resp(1, exception = RuntimeException("测试异常")))
        }.map { Resp(it.getOrThrow()) }
            .catch {
                emit(Resp(data = 1001, exception = it))
                println("catch: ${it.message}")
            }
            .onSuccess {
                println("onSuccess: $it")
            }.onFail {
                println("onFail: ${it.errorMsg}")
            }.collect()
    }

    @Test
    fun test_catch_throw_api_exception_3() = runBlocking {
        flow<Resp<Int>> {
            throw RuntimeException("测试异常")
        }.map { Resp(it.getOrThrow()) }
            .catch {
                emit(Resp(data = 1001, exception = it))
                println("catch: ${it.message}")
            }
            .onSuccess {
                println("onSuccess: $it")
            }.onFail {
                println("onFail: ${it.errorMsg}")
            }.collect()
    }
}