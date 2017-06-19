package com.example.jakubveverka.sportapp.ViewModels

import android.support.v4.app.FragmentActivity
import com.example.jakubveverka.sportapp.Activities.LauncherActivity
import com.example.jakubveverka.sportapp.Fragments.CreateEventFragment
import com.example.jakubveverka.sportapp.Fragments.EventsFragment
import com.example.jakubveverka.sportapp.R
import com.example.jakubveverka.sportapp.Utils.UsersManager

/**
 * Created by jakubveverka on 13.06.17.
 */
class SportEventsActivityViewModel(val mActivity: FragmentActivity) {

    val TAG_CREATE_EVENT_FRAGMENT = "create_event_fragment"
    val TAG_EVENTS_FRAGMENT = "events_fragment"

    private var mCreateEventFragment: CreateEventFragment? = null

    private var mEventsFragment: EventsFragment? = null

    /**
     * Method handles click on nav menu items.
     */
    fun processNavigationItemSelected(id: Int) {
        if (id == R.id.nav_sign_out) {
            UsersManager.signOutUser(mActivity.applicationContext)
            mActivity.startActivity(LauncherActivity.createIntent(mActivity))
            mActivity.finish()
        } else if(id == R.id.nav_create_event && !mCreateEventFragment!!.isVisible) {
            val transaction = mActivity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.body_fragment_container, mCreateEventFragment, TAG_CREATE_EVENT_FRAGMENT)
            transaction.addToBackStack(null)
            transaction.commit()
        } else if(id == R.id.nav_events_list && mCreateEventFragment!!.isVisible) {
            /** show events list just by popping back stack */
            mActivity.supportFragmentManager.popBackStackImmediate()
        }
    }

    /**
     * Method for restoring (if fragment can be restored) and displaying required fragment
     */
    fun restoreAndShowFragments() {
        val fm = mActivity.supportFragmentManager
        mCreateEventFragment = fm.findFragmentByTag(TAG_CREATE_EVENT_FRAGMENT) as? CreateEventFragment
        mEventsFragment = fm.findFragmentByTag(TAG_EVENTS_FRAGMENT) as? EventsFragment

        if(mEventsFragment == null) {
            mEventsFragment = EventsFragment()
            fm.beginTransaction().add(R.id.body_fragment_container, mEventsFragment, TAG_EVENTS_FRAGMENT).commit()
        }

        if(mCreateEventFragment == null) {
            mCreateEventFragment = CreateEventFragment()
        }
    }

    fun onDateSelected(year: Int, month: Int, day: Int) {
        mCreateEventFragment?.setDate(year, month, day)
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        mCreateEventFragment?.setTime(hour, minute)
    }

    /**
     * If event is created, pop back stack to show events list fragment and reload its data
     */
    fun onEventCreated() {
        mActivity.supportFragmentManager.popBackStackImmediate()
    }

    fun  getLoggedInText(): String {
        return String.format(mActivity.getString(R.string.logged_in_as), UsersManager.getUsersLoggedInName())
    }

}
