package com.example.ktest.ui.activities.main

import com.example.ktest.R
import com.example.ktest.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun getNavHostFragmentRes(): Int = R.id.fragment_container_main

    override fun initViewsAndCallbacks() {
        //init view properties and callbacks

        //set default selected item
        bottom_menu.setItemSelected(R.id.item_matrix,true)

        bottom_menu.setOnItemSelectedListener {
            when(it){
                R.id.item_matrix -> navController.navigate(R.id.matrixFragment)
                R.id.item_people -> navController.navigate(R.id.peopleFragment)
                R.id.item_ruler -> navController.navigate(R.id.rulerFragment)
                R.id.item_settings -> navController.navigate(R.id.settingFragment)
            }
        }


    }



}
