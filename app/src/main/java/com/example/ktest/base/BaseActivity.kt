package com.example.ktest.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ktest.data.sharedpref.SharedPreferencesManager
import com.example.ktest.utils.common.AppConstants.NULL_INDEX
import com.example.ktest.utils.extensions.safeRun
import com.example.ktest.utils.locale.LocaleContextWrapper
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import org.koin.android.ext.android.inject
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity() {


    val viewModel by lazy {
        ViewModelProvider(this).get(getViewModelClass())
    }

    lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private val spManager: SharedPreferencesManager by inject()

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): Class<V> = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        initViewsAndCallbacks()
        initObservables()
        initNavigator()
    }

    override fun attachBaseContext(base: Context?) {
        safeRun(
            {
                val newBase = ViewPumpContextWrapper.wrap(base!!)
                super.attachBaseContext(LocaleContextWrapper.wrap(newBase, spManager.getAppLanguage()))
            },
            { super.attachBaseContext(base) }
        )

    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    @IdRes
    abstract fun getNavHostFragmentRes(): Int

    abstract fun initViewsAndCallbacks()

    open fun initObservables() {}

    private fun initNavigator() {
        if (getNavHostFragmentRes() == NULL_INDEX)
            return

        //NavHostFragment needs to be updated with a new nav graph when you have more than 1 graphs
        navHostFragment =
            supportFragmentManager.findFragmentById(getNavHostFragmentRes()) as NavHostFragment
        //This will make our navController accessible from any fragment where we have a reference to mainActivity
        navController = navHostFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        return navController.navigateUp()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }


    @Suppress("DEPRECATION")
    val mProgressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    fun showLoading() {
        hideLoading()
        mProgressDialog.show()
    }

    fun hideLoading() {
        if (mProgressDialog.isShowing) {
            mProgressDialog.cancel()
        }
    }


}