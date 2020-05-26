package com.example.ktest.ui.activities.language

import com.example.ktest.base.BaseViewModel
import com.example.ktest.utils.common.AppConstants
import org.koin.core.inject

class LanguageViewModel: BaseViewModel() {
    private val repository: LanguageRepository by inject()

    fun setAppLanguagePersian() = repository.setAppLanguage(AppConstants.LANGUAGE_CODE_FA)

    fun setAppLanguageEnglish() = repository.setAppLanguage(AppConstants.LANGUAGE_CODE_EN)
}