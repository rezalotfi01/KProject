package com.example.ktest.data.models

sealed class BaseSPModel

data class SettingModel(var appLang: String = "") : BaseSPModel()

data class RelationModel(var person: PersonModel, var relatives: List<String>)

