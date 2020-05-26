package com.example.ktest.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel :  ViewModel(), KoinComponent {
    val disposables : CompositeDisposable by inject()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}