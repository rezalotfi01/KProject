package com.example.ktest.ui.fragments.ruler

import androidx.fragment.app.Fragment
import com.example.ktest.R
import com.example.ktest.base.BaseFragment
import com.example.ktest.utils.extensions.setDirectionLTR
import com.example.ktest.utils.view.DistanceUnit
import kotlinx.android.synthetic.main.fragment_ruler.*

/**
 * A simple [Fragment] subclass.
 */
class RulerFragment : BaseFragment<RulerViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_ruler

    override fun initViewsAndCallbacks() {
        segGroupOrientation.setDirectionLTR()
        segGroupUnit.setDirectionLTR()

        segGroupOrientation.setOnPositionChangedListener {
            rotateLayout.angle = if (it == 0) 0 else -90
        }
        segGroupUnit.setOnPositionChangedListener {
            ruler.unit = if (it == 0)
                DistanceUnit.CM
            else
                DistanceUnit.IN
        }
    }

}
