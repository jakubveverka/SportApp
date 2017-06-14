package com.example.jakubveverka.sportapp.Activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.jakubveverka.sportapp.R
import com.example.jakubveverka.sportapp.Utils.SnackbarUtils
import com.example.jakubveverka.sportapp.Utils.bindView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.ResultCodes
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class LauncherActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 100

    private var mAuth: FirebaseAuth? = null

    private val viewContainer: View by bindView(R.id.ll_activity_launcher_container)

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent()
            intent.setClass(context, LauncherActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        mAuth = FirebaseAuth.getInstance()

        /** check if user is sign in */
        if (mAuth?.currentUser != null) {
            /** if user is sign in start activity for logged user */
            startActivity(SportEventsActivity.createIntent(this))
            finish()
            return
        }
    }


    /**
     * Method for handling click on Sign In button
     * If button is clicked start Firebase auth UI activity where user can sign in
     */
    fun signInClicked(view: View) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(Arrays.asList(AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN)
    }

    /**
     * Method for processing Auth result from Firebase AuthUI activity.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                /** sign in OK -> start SportEventsActivity */
                startActivity(SportEventsActivity.createIntent(this, response!!))
                finish()
                return
            } else {
                /** some error, show error in snack bar */
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    SnackbarUtils.showSnackbar(viewContainer, R.string.sign_in_cancelled)
                    return
                }

                if (response.errorCode == ErrorCodes.NO_NETWORK) {
                    SnackbarUtils.showSnackbar(viewContainer, R.string.no_internet_connection)
                    return
                }

                if (response.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    SnackbarUtils.showSnackbar(viewContainer, R.string.unknown_error)
                    return
                }
            }
        }
    }
}
