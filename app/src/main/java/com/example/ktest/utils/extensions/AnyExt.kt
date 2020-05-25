package com.example.ktest.utils.extensions

import com.example.ktest.BuildConfig
import timber.log.Timber


fun Any.logE(message: String) {
    if (BuildConfig.DEBUG) {
        Timber.tag("KtestApp").e("${javaClass.simpleName}, $message")
    }
}

fun Exception.printError() {
    if (message != null)
        logE(message.orEmpty())
}

inline fun Any.safeRun(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        e.printError()
    }
}

inline fun Any.safeRun(block: () -> Unit, blockWhenError: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        e.printError()
        blockWhenError()
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> safeCastAndReturn(resultVal: Any, resultValOnError: T): T{
    return try {
        resultVal as T
    }catch (e: Exception){
        e.printError()
        resultValOnError
    }
}