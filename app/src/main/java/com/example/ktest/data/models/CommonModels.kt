package com.example.ktest.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class PersonModel(@Id var id: Long = 0, var firstName: String, var lastName: String)