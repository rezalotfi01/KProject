package com.example.ktest.data.db

import com.example.ktest.data.models.PersonModel
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import io.objectbox.rx.RxQuery
import io.reactivex.Observable

class DatabaseManager(private val store: BoxStore) {

    fun insertPeopleDetails(people: List<PersonModel>){
        val peopleBox = store.boxFor<PersonModel>()
        peopleBox.removeAll()
        peopleBox.put(people)
    }

    fun getPeopleData(): Observable<MutableList<PersonModel>> = RxQuery.observable(store.boxFor<PersonModel>().query{})
}