package com.example.ktest.ui.fragments.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.ktest.base.BaseViewModel
import com.example.ktest.data.models.PersonModel
import com.example.ktest.data.models.RelationModel
import com.example.ktest.data.net.RestCallback
import com.example.ktest.utils.extensions.containsEach
import com.example.ktest.utils.extensions.forceRefresh
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign

class PeopleViewModel : BaseViewModel() {

    //peopleLiveData listens to DB and will be notified if any changes occur in the people table of Database
    init {
        disposables += db.getPeopleData()
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                peopleLiveData.value = it
            }.doOnError {
                error.value = it.localizedMessage
            }.subscribe()
    }

    val peopleLiveData = MutableLiveData<List<PersonModel>>()

    val relationsLiveData = Transformations.map(peopleLiveData) {
        getRelatives(it)
    }

    val error = MutableLiveData<String>()

    fun loadPeopleData() = net.getPeopleOnline(object : RestCallback<List<PersonModel>> {
        override fun onResponseData(t: List<PersonModel>) {
            storePeopleInDB(t)
        }

        override fun onFailureData(errorMessage: String) {
            error.value = errorMessage
            peopleLiveData.forceRefresh()
        }
    })

    private fun storePeopleInDB(data: List<PersonModel>) = db.insertPeopleDetails(data)

    private fun getRelatives(people: List<PersonModel>): List<RelationModel> {
        val result = mutableListOf<RelationModel>()

        people.forEach {
            val relatedPeople = mutableListOf<String>()
            for (p in people) {
                if (it.firstName == p.firstName && it.lastName == p.lastName)
                    continue
                if (isRelated(it.lastName,p.lastName))
                    relatedPeople.add(p.firstName)
            }

            result.add(RelationModel(it, relatedPeople))
        }

        return result
    }

    private fun isRelated(first: String, second: String) = first == second || first.containsEach(second.split('-'))
            || first.containsEach(second.split("-"))

}