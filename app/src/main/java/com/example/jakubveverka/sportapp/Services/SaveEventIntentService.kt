package com.example.jakubveverka.sportapp.Services

import android.app.IntentService
import android.content.Intent
import com.example.jakubveverka.sportapp.Models.DataManager
import com.example.jakubveverka.sportapp.Models.Event
import android.support.v4.content.LocalBroadcastManager
import com.example.jakubveverka.sportapp.Fragments.CreateEventFragment
import com.example.jakubveverka.sportapp.Utils.Constants


/**
 * Created by jakubveverka on 14.06.17.
 * Intent service for saving event to database or firebase
 */
class SaveEventIntentService: IntentService("SaveEventIntentService") {

    companion object {
        const val EVENT_EXTRA_NAME = "event_extra"
    }

    override fun onHandleIntent(intent: Intent?) {
        if(intent == null) return
        val event = intent.getParcelableExtra<Event>(EVENT_EXTRA_NAME)

        val status = if (DataManager(this).saveEvent(event)) Constants.STATE_SUCCESS else Constants.STATE_FAILED
        val localIntent = Intent(CreateEventFragment.CREATING_EVENT_FINISHED_BROADCAST_ACTION)
                .putExtra(CreateEventFragment.CREATING_EVENT_STATUS, status)
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent)
    }
}