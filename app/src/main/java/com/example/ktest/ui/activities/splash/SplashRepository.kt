package com.example.ktest.ui.activities.splash

import com.example.ktest.base.BaseRepository

class SplashRepository : BaseRepository() {
   fun isLanguageSelected() = sp.getAppLanguage().trim().isNotEmpty()
}