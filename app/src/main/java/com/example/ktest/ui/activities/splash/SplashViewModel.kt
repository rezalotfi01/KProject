package com.example.ktest.ui.activities.splash

import com.example.ktest.base.BaseViewModel
import org.koin.core.inject

class SplashViewModel : BaseViewModel() {
    private val repository: SplashRepository by inject()

    fun isLanguageSelected() = repository.isLanguageSelected()
}