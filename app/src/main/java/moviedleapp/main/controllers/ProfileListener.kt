package moviedleapp.main.controllers

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity

interface ProfileListener {
    fun hideLoginButton()

    fun onSignIn(image : Drawable,profileName :String)

    fun getFragmentActivity() : FragmentActivity

    fun getActivityResultLauncher() : ActivityResultLauncher<Intent>

    fun onLogout()

}