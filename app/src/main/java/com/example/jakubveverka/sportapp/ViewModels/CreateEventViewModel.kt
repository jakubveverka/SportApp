package com.example.jakubveverka.sportapp.ViewModels

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.v4.app.FragmentActivity
import android.view.View
import com.example.jakubveverka.sportapp.FragmentDialogs.DatePickerFragmentDialog
import com.example.jakubveverka.sportapp.FragmentDialogs.TimePickerFragmentDialog
import com.example.jakubveverka.sportapp.R

/**
 * Created by jakubveverka on 13.06.17.
 */
class CreateEventViewModel(val context: Context) {
    private var startHour: Int = 0
    private var startMinute: Int = 0
    private var startYear: Int = 0
    private var startMonth: Int = 0
    private var startDay: Int = 0

    private var endHour: Int = 0
    private var endMinute: Int = 0
    private var endYear: Int = 0
    private var endMonth: Int = 0
    private var endDay: Int = 0

    var settingStartDate = true

    val startDateTimeString: ObservableField<String> = ObservableField()
    val endDateTimeString: ObservableField<String> = ObservableField()

    val name: ObservableField<String> = ObservableField()
    val place: ObservableField<String> = ObservableField()

    val selectedStoragePosition: ObservableInt = ObservableInt()

    val createEventError: ObservableField<String> = ObservableField()

    fun setDate(year: Int, month: Int, day: Int) {
        if(settingStartDate) setStartDate(year, month, day)
        else setEndDate(year, month, day)
    }

    fun setTime(hour: Int, minute: Int) {
        if(settingStartDate) setStartTime(hour, minute)
        else setEndTime(hour, minute)
    }


    fun setStartDate(year: Int, month: Int, day: Int) {
        startYear = year
        startMonth = month
        startDay = day
    }

    fun setStartTime(hour: Int, minute: Int) {
        startHour = hour
        startMinute = minute
        fillStartDateTimeString()
    }

    private fun fillStartDateTimeString() {
        startDateTimeString.set("$startHour:$startMinute $startDay.$startMonth.$startYear")
    }

    fun setEndDate(year: Int, month: Int, day: Int) {
        endYear = year
        endMonth = month
        endDay = day
    }

    fun setEndTime(hour: Int, minute: Int) {
        endHour = hour
        endMinute = minute
        fillEndDateTimeString()
    }

    private fun fillEndDateTimeString() {
        endDateTimeString.set("$endHour:$endMinute $endDay.$endMonth.$endYear")
    }

    fun showDatePickerFragmentDialog(activity: FragmentActivity) {
        val newFragment = DatePickerFragmentDialog()
        newFragment.show(activity.supportFragmentManager, "datePicker")
    }

    fun showTimePickerFragmentDialog(activity: FragmentActivity) {
        val newFragment = TimePickerFragmentDialog()
        newFragment.show(activity.supportFragmentManager, "timePicker")
    }

    fun createEvent(view: View) {
        if(name.get() == null ||
                place.get() == null ||
                startDateTimeString.get() == null ||
                endDateTimeString.get() == null) {
            createEventError.set(context.getString(R.string.all_fields_are_required_error))
            return
        }
        //TODOt
    }

}
