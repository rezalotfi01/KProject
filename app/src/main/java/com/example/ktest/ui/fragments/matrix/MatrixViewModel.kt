package com.example.ktest.ui.fragments.matrix

import androidx.lifecycle.MutableLiveData
import com.example.ktest.base.BaseViewModel
import org.koin.core.inject

class MatrixViewModel : BaseViewModel() {
    val repository: MatrixRepository by inject()
    var matrixVal = MutableLiveData<MutableList<MutableList<Int>>>()

    fun loadMatrixVal(){
        matrixVal.value = repository.getMatrixFromFakeSource()
    }
}