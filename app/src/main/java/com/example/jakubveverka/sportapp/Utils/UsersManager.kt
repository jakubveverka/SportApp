package com.example.jakubveverka.sportapp.Utils

import android.content.Context
import com.example.jakubveverka.sportapp.Models.DataManager
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by jakubveverka on 19.06.17.
 */
class UsersManager {
    companion object {
        fun signOutUser(context: Context) {
            DataManager.init(context, FirebaseAuth.getInstance().currentUser!!.uid).stopListeningOnFirebaseDb()
            FirebaseAuth.getInstance().signOut()
        }

        fun  getUsersLoggedInName(): String {
            val user = FirebaseAuth.getInstance().currentUser!!
            if(user.email != null) return user.email!!
            if(user.displayName != null) return user.displayName!!

            return "NoName"
        }
    }
}