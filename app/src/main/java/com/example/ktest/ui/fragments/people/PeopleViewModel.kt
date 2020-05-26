package com.example.ktest.ui.fragments.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.ktest.base.BaseViewModel
import com.example.ktest.data.models.PersonModel
import com.example.ktest.data.models.RelationModel
import com.example.ktest.utils.extensions.containsEach
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import org.koin.core.inject

class PeopleViewModel : BaseViewModel() {
    private val repository: PeopleRepository by inject()

    //peopleLiveData listens to DB and will be notified if any changes occur in the people table of Database
    init {
        disposables += repository.getPeopleData()
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (it.isNotEmpty())
                    peopleLiveData.value = it
            }.doOnError {
                error.value = it.localizedMessage
            }.subscribe()
        loadPeopleData()
    }

    val peopleLiveData = MutableLiveData<List<PersonModel>>()
    val relationsLiveData = Transformations.map(peopleLiveData) {
        getRelatives(it)
    }

    val error = MutableLiveData<String>()

    fun loadPeopleData() {
        disposables += repository.loadPeopleData()
    }

    private fun getRelatives(people: List<PersonModel>): List<RelationModel> {
        val result = mutableListOf<RelationModel>()

        people.forEach {
            val relatedPeople = mutableListOf<String>()
            for (p in people) {
                if (it.firstName == p.firstName && it.lastName == p.lastName)
                    continue
                if (isRelated(it.lastName, p.lastName))
                    relatedPeople.add(p.firstName)
            }

            result.add(RelationModel(it, relatedPeople))
        }

        return result
    }

    private fun isRelated(first: String, second: String) =
        first == second || first.containsEach(second.split('-'))
                || first.containsEach(second.split("-"))

}