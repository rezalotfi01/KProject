package com.example.ktest.utils.locale

import com.example.ktest.data.sharedpref.SharedPreferencesManager
import org.koin.core.KoinComponent
import org.koin.core.inject


object LocaleUtils : KoinComponent{
    fun getCurrentLanguage(): String{
        val sp: SharedPreferencesManager by inject()
        return sp.getSettingModel().appLang
    }
}