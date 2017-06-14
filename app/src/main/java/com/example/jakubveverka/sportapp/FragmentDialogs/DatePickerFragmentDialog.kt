package com.example.jakubveverka.sportapp.FragmentDialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import java.util.*

/**
 * Created by jakubveverka on 13.06.17.
 */
class DatePickerFragmentDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    var mCallback: OnDateSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as OnDateSelectedListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnDateSelectedListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        mCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        mCallback?.onDateSelected(year, month, day)
    }

    // Container Activity must implement this interface
    interface OnDateSelectedListener {
        fun onDateSelected(year: Int, month: Int, day: Int)
    }

}
