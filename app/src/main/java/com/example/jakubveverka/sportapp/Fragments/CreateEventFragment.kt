package com.example.jakubveverka.sportapp.Fragments


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.jakubveverka.sportapp.R
import android.databinding.DataBindingUtil
import com.example.jakubveverka.sportapp.FragmentDialogs.DatePickerFragmentDialog
import com.example.jakubveverka.sportapp.Utils.Constants
import com.example.jakubveverka.sportapp.ViewModels.CreateEventViewModel
import com.example.jakubveverka.sportapp.databinding.FragmentCreateEventBinding
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager


/**
 * Fragment for creating new Event
 */
class CreateEventFragment : Fragment() {

    companion object {
        fun newInstance(): CreateEventFragment {
            val fragment = CreateEventFragment()
            return fragment
        }
        const val CREATING_EVENT_FINISHED_BROADCAST_ACTION = "com.example.jakubveverka.sportapp.CREATING_EVENT_FINISHED_BROADCAST_ACTION"
        const val CREATING_EVENT_STATUS = "com.example.jakubveverka.sportapp.CREATING_EVENT_STATUS"
    }

    protected val mViewModel: CreateEventViewModel by lazy {
        CreateEventViewModel(activity.applicationContext)
    }

    private val mEventCreatedReceiver: EventCreatedReceiver by lazy {
        EventCreatedReceiver()
    }

    var mCallback: CreateEventFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as CreateEventFragmentListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement CreateEventFragmentListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        mCallback = null
    }

    private var mBinding: FragmentCreateEventBinding? = null

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

    override fun onResume() {
        super.onResume()
        val statusIntentFilter = IntentFilter(CREATING_EVENT_FINISHED_BROADCAST_ACTION)
        LocalBroadcastManager.getInstance(activity).registerReceiver(mEventCreatedReceiver, statusIntentFilter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(mEventCreatedReceiver)
    }

    fun  setDate(year: Int, month: Int, day: Int) {
        mViewModel.setDate(year, month, day)
        mViewModel.showTimePickerFragmentDialog(activity)
    }

    fun  setTime(hour: Int, minute: Int) {
        mViewModel.setTime(hour, minute)
    }

    interface CreateEventFragmentListener {
        fun eventCreated()
    }

    private inner class EventCreatedReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent == null) return
            val status = intent.getIntExtra(CREATING_EVENT_STATUS, Constants.NOT_FOUND)
            if(status == Constants.NOT_FOUND) return
            if(!mViewModel.handleCreatingEventFinishedStatus(status)) {
                mCallback?.eventCreated()
            }
        }
    }

}

