package com.example.ktest.ui.activities.language

import com.example.ktest.R
import com.example.ktest.base.BaseActivity
import com.example.ktest.ui.activities.main.MainActivity
import com.example.ktest.utils.common.AppConstants.NULL_INDEX
import com.example.ktest.utils.extensions.openActivity
import kotlinx.android.synthetic.main.activity_language.*

class LanguageActivity : BaseActivity<LanguageViewModel>() {

    override fun getLayoutRes()  = R.layout.activity_language

    override fun getNavHostFragmentRes(): Int = NULL_INDEX

    override fun initViewsAndCallbacks() {
        cardPersian.setOnClickListener {
            viewModel.setAppLanguagePersian()
            goToMain()
        }

        cardEnglish.setOnClickListener {
            viewModel.setAppLanguageEnglish()
            goToMain()
        }
    }

    private fun goToMain(){
        openActivity(MainActivity::class.java)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
