package com.example.jakubveverka.sportapp.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.jakubveverka.sportapp.R
import android.databinding.DataBindingUtil
import com.example.jakubveverka.sportapp.ViewModels.CreateEventViewModel
import com.example.jakubveverka.sportapp.Utils.bindView
import com.example.jakubveverka.sportapp.databinding.FragmentCreateEventBinding

/**
 * Fragment for creating new Event
 */
class CreateEventFragment : Fragment() {

    private val mViewModel: CreateEventViewModel by lazy {
        CreateEventViewModel(activity.applicationContext)
    }


    private var mBinding: FragmentCreateEventBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_create_event, container, false)
        val view = mBinding!!.root
        mBinding!!.viewModel = mViewModel

        val pickStartDateButton = view.findViewById(R.id.btn_pick_start_date)
        val pickEndDateButton = view.findViewById(R.id.btn_pick_end_date)

        pickStartDateButton.setOnClickListener {
            mViewModel.settingStartDate = true
            mViewModel.showDatePickerFragmentDialog(activity)
        }

        pickEndDateButton.setOnClickListener {
            mViewModel.settingStartDate = false
            mViewModel.showDatePickerFragmentDialog(activity)
        }

        return view
    }

    companion object {
        fun newInstance(): CreateEventFragment {
            val fragment = CreateEventFragment()
            return fragment
        }
    }

    fun  setDate(year: Int, month: Int, day: Int) {
        mViewModel.setDate(year, month, day)
        mViewModel.showTimePickerFragmentDialog(activity)
    }

    fun  setTime(hour: Int, minute: Int) {
        mViewModel.setTime(hour, minute)
    }

}

