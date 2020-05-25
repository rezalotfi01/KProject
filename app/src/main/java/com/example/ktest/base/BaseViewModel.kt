package com.example.ktest.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.ktest.data.db.DatabaseManager
import com.example.ktest.data.net.NetManager
import com.example.ktest.data.sharedpref.SharedPreferencesManager
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel :  ViewModel(), KoinComponent {
    val context: Context by inject()

    val net: NetManager by inject()
    val db: DatabaseManager by inject()
    val sp: SharedPreferencesManager by inject()

    val disposables : CompositeDisposable by inject()


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}