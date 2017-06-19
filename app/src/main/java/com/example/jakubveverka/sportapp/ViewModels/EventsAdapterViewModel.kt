package com.example.jakubveverka.sportapp.ViewModels

import android.content.Context
import com.example.jakubveverka.sportapp.Entities.Event
import java.text.DateFormat
import java.util.*

/**
 * Created by jakubveverka on 19.06.17.
 */
class EventsAdapterViewModel(val context: Context) {

    val dateFormat: DateFormat by lazy {
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
    }

    fun formatDateInMillis(millis: Long): String {
        return dateFormat.format(Date(millis))
    }

    fun  getBackgroundColorForEvent(event: Event): Int {
        when(event.storage) {
            Event.EventStorage.LOCAL -> return context.resources.getColor(android.R.color.holo_green_light)
            Event.EventStorage.FIREBASE -> return context.resources.getColor(android.R.color.holo_blue_light)
            else -> throw Exception("Not supported EventStorage type")
        }
    }
}