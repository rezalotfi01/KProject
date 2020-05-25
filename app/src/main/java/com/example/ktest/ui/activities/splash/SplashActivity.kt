package com.example.ktest.ui.activities.splash


import com.example.ktest.R
import com.example.ktest.base.BaseActivity
import com.example.ktest.ui.activities.language.LanguageActivity
import com.example.ktest.ui.activities.main.MainActivity
import com.example.ktest.utils.common.AppConstants.NULL_INDEX
import com.example.ktest.utils.extensions.openActivity

class SplashActivity : BaseActivity<SplashViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_splash

    override fun getNavHostFragmentRes(): Int = NULL_INDEX

    override fun initViewsAndCallbacks() {
        if (viewModel.isLanguageSelected())
            openActivity(MainActivity::class.java)
        else
            openActivity(LanguageActivity::class.java)
        finish()
    }

}
