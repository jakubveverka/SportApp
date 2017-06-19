package com.example.jakubveverka.sportapp.ViewModels

import android.content.Context
import com.example.jakubveverka.sportapp.Adapters.EventsRecyclerViewAdapter
import com.example.jakubveverka.sportapp.Models.DataManager
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by jakubveverka on 17.06.17.
 */
class EventsViewModel(val context: Context) {

    fun getEventsAdapter() : EventsRecyclerViewAdapter {
        //return DataManager.init(context).getAllEventsAdapterOfUserWithUid(userUid)
        return DataManager.init(context, getUserUid()).getAllEventsAdapterOfUserWithUid()
    }

    /*fun startListeningForEventsInFirebase() {
        DataManager.init(context, getUserUid()).startListeningForEventsInFirebase()
    }

    fun stopListeningForEventsInFirebase() {
        DataManager.init(context, getUserUid()).stopListeningForEventsInFirebase()
    }*/

    private fun getUserUid(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

}
