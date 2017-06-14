package com.example.jakubveverka.sportapp.Activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.jakubveverka.sportapp.R
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.IdpResponse
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.example.jakubveverka.sportapp.FragmentDialogs.DatePickerFragmentDialog
import com.example.jakubveverka.sportapp.FragmentDialogs.TimePickerFragmentDialog
import com.example.jakubveverka.sportapp.Fragments.CreateEventFragment
import com.example.jakubveverka.sportapp.Fragments.EventsFragment
import com.example.jakubveverka.sportapp.Utils.SnackbarUtils
import com.example.jakubveverka.sportapp.Utils.bindView
import com.example.jakubveverka.sportapp.ViewModels.SportEventsActivityViewModel

/**
 * Activity for logged users.
 * Shows 2 fragments,first fragment with events list, second fragments is for creating new event
 */
class SportEventsActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        TimePickerFragmentDialog.OnTimeSelectedListener,
        DatePickerFragmentDialog.OnDateSelectedListener,
        CreateEventFragment.CreateEventFragmentListener {

    /** bind view using Kotter knife */
    private val mCoorLayout: View by bindView(R.id.cl_sport_events)

    private val mViewModel: SportEventsActivityViewModel by lazy {
        SportEventsActivityViewModel(this)
    }

    companion object {
        fun createIntent(context: Context, idpResponse: IdpResponse? = null): Intent {
            val intent: Intent
            if(idpResponse != null) intent = IdpResponse.getIntent(idpResponse)
            else intent = Intent()
            intent.setClass(context, SportEventsActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport_events)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        val header = navigationView.getHeaderView(0)
        val twUsersEmail = header.findViewById(R.id.tw_nav_header_users_email) as TextView
        twUsersEmail.text = String.format(getString(R.string.logged_in_as), FirebaseAuth.getInstance().currentUser!!.email)

        mViewModel.restoreAndShowFragments()
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.sport_events, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        mViewModel.processNavigationItemSelected(id)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDateSelected(year: Int, month: Int, day: Int) {
        mViewModel.onDateSelected(year, month, day)
    }

    override fun onTimeSelected(hour: Int, minute: Int) {
        mViewModel.onTimeSelected(hour, minute)
    }

    override fun eventCreated() {
        mViewModel.onEventCreated()
        SnackbarUtils.showSnackbar(mCoorLayout, R.string.event_created)
    }
}
