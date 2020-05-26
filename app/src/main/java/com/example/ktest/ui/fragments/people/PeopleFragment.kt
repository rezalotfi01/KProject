package com.example.ktest.ui.fragments.people

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.ktest.R
import com.example.ktest.base.BaseFragment
import com.example.ktest.utils.extensions.toStringList
import com.example.ktest.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_people.*

/**
 * A simple [Fragment] subclass.
 */
class PeopleFragment : BaseFragment<PeopleViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_people

    override fun initViewsAndCallbacks() {
        skeletonGroup.post {
            skeletonGroup.startAnimation()
        }

        mainSwipeLayout.setOnRefreshListener {
            viewModel.loadPeopleData()
        }
    }

    override fun onResume() {
        super.onResume()
//        viewModel.loadPeopleData()
    }


    override fun initObservables() {
        viewModel.peopleLiveData.observe(viewLifecycleOwner, Observer {
            var peopleListStr = ""
            it.forEach { model ->
                peopleListStr += model.firstName + " " + model.lastName + "\n"
            }
            txtPeopleList.text = peopleListStr
        })

        viewModel.relationsLiveData.observe(viewLifecycleOwner, Observer {
            var relationsStr = ""
            it.forEach { rModel ->
                relationsStr +=
                    "\n" + getString(R.string.user) + " " + rModel.person.firstName + " " + getString(R.string.related_to) +
                            " " + if (rModel.relatives.isNotEmpty()) rModel.relatives.toStringList(", ") else getString(R.string.no_one)  +
                            "\n"
            }
            txtRelations.text = relationsStr

            hideSkeleton()
            mainSwipeLayout.isRefreshing = false
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            toast(getString(R.string.error_in_loading_data))
        })
    }

    private fun hideSkeleton() {
        skeletonGroup.finishAnimation()
    }


}
