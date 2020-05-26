package com.example.ktest.ui.activities.language

import com.example.ktest.base.BaseRepository

class LanguageRepository : BaseRepository() {

    fun setAppLanguage(language: String) = sp.setAppLanguage(language)

}