package com.example.jakubveverka.sportapp.ViewModels


import android.support.v4.app.FragmentActivity
import com.example.jakubveverka.sportapp.Activities.LauncherActivity
import com.example.jakubveverka.sportapp.Fragments.CreateEventFragment
import com.example.jakubveverka.sportapp.Fragments.EventsFragment
import com.example.jakubveverka.sportapp.R
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by jakubveverka on 13.06.17.
 */
class SportEventsActivityViewModel(val mActivity: FragmentActivity) {

    var mIsCreateEventFragmentVisible = false

    private val mCreateEventFragment: CreateEventFragment by lazy {
        CreateEventFragment()
    }

    private val mEventsFragment: EventsFragment by lazy {
        EventsFragment()
    }

    fun processNavigationItemSelected(id: Int) {
        if (id == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut()
            mActivity.startActivity(LauncherActivity.createIntent(mActivity))
            mActivity.finish()
        } else if(id == R.id.nav_create_event && !mIsCreateEventFragmentVisible) {
            val transaction = mActivity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.body_fragment_container, mCreateEventFragment)
            transaction.addToBackStack(null)
            transaction.commit()
            mIsCreateEventFragmentVisible = true
        } else if(id == R.id.nav_events_list && mIsCreateEventFragmentVisible) {
            mActivity.supportFragmentManager.popBackStackImmediate()
            mIsCreateEventFragmentVisible = false
        }
    }

    fun showEventsFragment() {
        val transaction = mActivity.supportFragmentManager.beginTransaction()
        transaction.add(R.id.body_fragment_container, mEventsFragment)
        transaction.commit()
    }

    fun onDateSelected(year: Int, month: Int, day: Int) {
        mCreateEventFragment.setDate(year, month, day)
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        mCreateEventFragment.setTime(hour, minute)
    }

    fun onEventCreated() {
        mActivity.supportFragmentManager.popBackStackImmediate()
        mIsCreateEventFragmentVisible = false
        mEventsFragment.reloadEvents()
    }

}
