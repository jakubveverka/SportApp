package com.example.jakubveverka.sportapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.jakubveverka.sportapp.R
import com.example.jakubveverka.sportapp.Utils.MyLog
import com.example.jakubveverka.sportapp.ViewModels.EventsViewModel


class EventsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    private val mViewModel: EventsViewModel by lazy {
        EventsViewModel(activity.applicationContext)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        MyLog.d("Called onCreateView in EventsFragment")
        val view = inflater!!.inflate(R.layout.fragment_events_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            val recyclerView = view
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = mViewModel.getEventsAdapter()
        }
        return view
    }
}
