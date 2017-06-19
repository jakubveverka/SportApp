package com.example.jakubveverka.sportapp.Utils

import android.util.Log

/**
 * Created by jakubveverka on 17.06.17.
 */
class MyLog {
    companion object {
        private val TAG = "SportApp"

        fun d(message: String) {
            Log.d(TAG, message)
        }
    }
}