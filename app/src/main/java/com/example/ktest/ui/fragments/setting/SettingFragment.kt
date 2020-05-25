package com.example.ktest.ui.fragments.setting

import androidx.fragment.app.Fragment
import com.example.ktest.R
import com.example.ktest.base.BaseFragment
import com.example.ktest.ui.activities.language.LanguageActivity
import com.example.ktest.utils.extensions.openActivity
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : BaseFragment<SettingViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_setting

    override fun initViewsAndCallbacks() {
        cardLanguage.setOnClickListener {
            requireContext().openActivity(LanguageActivity::class.java)
            requireActivity().finish()
        }
    }

}
