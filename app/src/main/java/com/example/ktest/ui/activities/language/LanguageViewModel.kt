package com.example.ktest.ui.activities.language

import com.example.ktest.base.BaseViewModel
import com.example.ktest.utils.common.AppConstants

class LanguageViewModel: BaseViewModel() {
    fun setAppLanguagePersian(){
        sp.setAppLanguage(AppConstants.LANGUAGE_CODE_FA)
    }

    fun setAppLanguageEnglish(){
        sp.setAppLanguage(AppConstants.LANGUAGE_CODE_EN)
    }
}