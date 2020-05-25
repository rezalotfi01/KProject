package com.example.ktest.data.sharedpref

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.example.ktest.data.models.SettingModel
import com.example.ktest.utils.extensions.safeRun

class SharedPreferencesManager (val prefs: SharedPreferences, val moshi: Moshi) {

    inline fun <reified T> saveObject(model: T){
        val adapter = moshi.adapter(T::class.java)
        val json = adapter.toJson(model)

        prefs.edit().putString(getSaveKey(T::class.java), json).apply()
    }

    fun <T> getSaveKey(cls: Class<T>) = cls.name

    fun <T> getObject (cls: Class<T> ) : T?  {
        var result: T? = null
        safeRun {
            val spStr = prefs.getString(getSaveKey(cls),null)

            val spJson = spStr!!

            if(spJson.replace(" ","") == "")
                return null

            val adapter = moshi.adapter(cls)

            result = adapter.fromJson(spJson)
        }

        return result
    }

    //---------------------------------------------------------------------------------

    fun getSettingModel() = getObject(SettingModel::class.java) ?: SettingModel()

    fun saveSettingModel(model: SettingModel) = saveObject(model)

    fun setAppLanguage(lang: String) = saveSettingModel(getSettingModel().apply { appLang = lang })

    fun getAppLanguage(): String = getSettingModel().appLang

}