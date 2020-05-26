package com.example.ktest.ui.fragments.matrix

import com.example.ktest.base.BaseRepository

class MatrixRepository : BaseRepository() {
    fun getMatrixFromFakeSource() : MutableList<MutableList<Int>>{
        val dim = 5
        var num = 0
        val numsList: MutableList<MutableList<Int>> =
            mutableListOf()
        for (i in 0 until dim) {
            numsList.add(arrayListOf())
            var j = 0
            while (j < dim) {
                numsList[i].add(num)
                j++
                num++
                //if you want test Method with other numbers comment above line and uncomment below line
//                num = (0..99).random()
            }
        }
        return numsList
    }
}