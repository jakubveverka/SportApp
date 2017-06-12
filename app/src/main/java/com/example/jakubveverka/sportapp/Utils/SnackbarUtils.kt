package com.example.jakubveverka.sportapp.Utils

import android.support.design.widget.Snackbar
import android.view.View
import com.example.jakubveverka.sportapp.R

/**
 * Created by jakubveverka on 10.06.17.
 */
object SnackbarUtils {
    fun showSnackbar(view: View, stringId: Int) = Snackbar.make(view, stringId, Snackbar.LENGTH_SHORT).show()
}
