package com.example.ktest.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ktest.utils.extensions.safeRun
import org.koin.android.ext.android.inject
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<V : BaseViewModel> : Fragment() {

    val viewModel by lazy {
        ViewModelProvider(this).get(getViewModelClass())
    }

    lateinit var rootView: View private set
    val appContext: Context by inject()
    val navController: NavController by lazy { findNavController() }

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): Class<V> = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(getLayoutRes(),null,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackPress()
        initViewsAndCallbacks()
        initObservables()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun initViewsAndCallbacks()

    open fun initObservables() {

    }

    private fun setupBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        safeRun {
            requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, callback)
        }
    }

    open fun onBackPressed() {
        safeRun {
            if (navController.graph.startDestination == navController.currentDestination!!.id)
                (activity as AppCompatActivity).finish()
            else
                navController.popBackStack()
        }
    }

    @Suppress("DEPRECATION")
    val mProgressDialog: ProgressDialog by lazy {
        ProgressDialog(activity)
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