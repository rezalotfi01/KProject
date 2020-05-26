package com.example.ktest.ui.fragments.people

import com.example.ktest.base.BaseRepository
import com.example.ktest.data.models.PersonModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PeopleRepository : BaseRepository() {

    fun loadPeopleData() = api.getPeopleDetails()
        .map { storePeopleInDB(it) }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe()

    fun getPeopleData() = db.getPeopleData()

    private fun storePeopleInDB(data: List<PersonModel>) = db.insertPeopleDetails(data)
}