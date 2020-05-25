package com.example.ktest.ui.activities.splash

import com.example.ktest.base.BaseViewModel

class SplashViewModel : BaseViewModel() {
    fun isLanguageSelected() = sp.getAppLanguage().trim().isNotEmpty()
}