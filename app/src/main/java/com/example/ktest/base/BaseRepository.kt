package com.example.ktest.base

import com.example.ktest.data.db.DatabaseManager
import com.example.ktest.data.net.ApiClient
import com.example.ktest.data.sharedpref.SharedPreferencesManager
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseRepository : KoinComponent {
    val api: ApiClient by inject()
    val db: DatabaseManager by inject()
    val sp: SharedPreferencesManager by inject()
}