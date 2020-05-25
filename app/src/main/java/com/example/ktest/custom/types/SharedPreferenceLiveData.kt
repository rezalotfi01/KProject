package com.example.ktest.custom.types

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.MutableLiveData
import com.example.ktest.data.sharedpref.SharedPreferencesManager
import org.koin.core.KoinComponent
import org.koin.core.get

class SharedPreferenceLiveData<T: Any>(private val defValue: T) : MutableLiveData<T>(), KoinComponent {

    private val sharedPrefsManager: SharedPreferencesManager = get()
    private val key: String = sharedPrefsManager.getSaveKey(defValue::class.java)

    private val preferenceChangeListener =
        OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (this@SharedPreferenceLiveData.key == key) {
                value = getValueFromPreferences(defValue)
            }
        }

    private fun getValueFromPreferences(defValue: T): T? {
        return sharedPrefsManager.getObject(defValue.javaClass)
    }

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(defValue)
        sharedPrefsManager.prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefsManager.prefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}