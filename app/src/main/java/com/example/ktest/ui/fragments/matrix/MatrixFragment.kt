package com.example.ktest.ui.fragments.matrix

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.ktest.R
import com.example.ktest.base.BaseFragment
import com.example.ktest.utils.extensions.*

import kotlinx.android.synthetic.main.fragment_matrix.*


/**
 * A simple [Fragment] subclass.
 */
class MatrixFragment : BaseFragment<MatrixViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_matrix

    override fun initViewsAndCallbacks() {
        setupRefreshLLayout()
    }


    override fun initObservables() {
        viewModel.matrixVal.observe(viewLifecycleOwner, Observer {
            tableInputMatrix.fillTableWithMatrix(it)
            txtOutputMatrix.text = calculateOutputMatrix(it)

            refreshLayout.isRefreshing = false
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMatrixVal()
    }

    private fun setupRefreshLLayout() {
        refreshLayout.setColorSchemeResources(
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.colorPrimaryDark
        )
        refreshLayout.setOnRefreshListener {
            viewModel.loadMatrixVal()
        }
    }


    private fun calculateOutputMatrix(list: MutableList<MutableList<Int>>): String {
        if (!list.isSquareMatrix()) {
            toast("Error: Your Data Matrix is not Square Matrix !")
            return "Error"
        }

        var result = ""
        val dim = list.size
        list.forEach { it.reverse() }
        for (k in 0 until dim * 2) {
            val row = mutableListOf<Int>()
            for (j in 0..k) {
                val i = k - j
                if (i < dim && j < dim) {
                    row += list[i][j]
                }
            }
            result += row.reversed().toStringList() + "\n"
        }

        return result
    }
}
