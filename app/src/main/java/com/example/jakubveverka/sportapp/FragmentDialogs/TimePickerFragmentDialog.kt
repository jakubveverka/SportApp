package com.example.jakubveverka.sportapp.FragmentDialogs

import android.app.Dialog
import android.widget.TimePicker
import android.text.format.DateFormat.is24HourFormat
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import java.util.*
import android.app.Activity
import android.content.Context
import android.support.v4.app.DialogFragment


/**
 * Created by jakubveverka on 13.06.17.
 * Fragment dialog for picking Time
 */
class TimePickerFragmentDialog : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    var mCallback: OnTimeSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as OnTimeSelectedListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnTimeSelectedListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        mCallback = null
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute,
                DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        mCallback?.onTimeSelected(hourOfDay, minute)
    }

    // Container Activity must implement this interface
    interface OnTimeSelectedListener {
        fun onTimeSelected(hour: Int, minute: Int)
    }

}
