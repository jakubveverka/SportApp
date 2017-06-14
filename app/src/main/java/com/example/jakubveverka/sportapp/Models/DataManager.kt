package com.example.jakubveverka.sportapp.Models

import android.content.Context
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by jakubveverka on 14.06.17.
 */
class DataManager(val context: Context) {
    fun saveEvent(event: Event): Boolean {
        when (event.storage) {
            Event.EventStorage.LOCAL -> return saveEventToDb(event)
            Event.EventStorage.FIREBASE -> return saveEventToFirebase(event)
            else -> {
                throw Exception("Not supported EventStorage type")
            }
        }
    }

    private fun saveEventToDb(event: Event): Boolean {
        return EventsDbHelper.getInstance(context).saveEvent(event)
    }

    private fun saveEventToFirebase(event: Event): Boolean {
        val database = FirebaseDatabase.getInstance().reference
        val eventsRef = database.child("users").child(event.userUid).child("events").push()
        eventsRef.setValue(event)
        return true
    }
}