package moviedleapp.main.controllers

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.network.Repository
import java.net.SocketTimeoutException

class ProfileFragmentController(
    private val profileListener: ProfileListener,
    private val googleLogInLauncher: ActivityResultLauncher<Intent>,
) {
    //const
    private val googleTag = "GOOGLE"
    private val serverClientId =
        "747713817851-sknc38jco126c6g11aiqjk5otbh1e28k.apps.googleusercontent.com"
    //others
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(serverClientId)
        .build()
    private var mGoogleSignInClient: GoogleSignInClient =
        GoogleSignIn.getClient(profileListener.getFragmentActivity(), gso)

    fun singIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        googleLogInLauncher.launch(signInIntent)
    }

    fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(profileListener.getFragmentActivity()) {
                Log.e(googleTag, "Successfully logged out")
            }
        profileListener.onLogout()
    }

    fun silentSignIn() {
            mGoogleSignInClient.silentSignIn()
                .addOnCompleteListener(profileListener.getFragmentActivity()) { task ->
                    handleSignInResult(
                        task
                    )
                }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken.toString()
            Log.i(googleTag, "id token")
            CoroutineScope(Dispatchers.Main).launch {
                try{
                    Repository.loginByGoogleToken(idToken)
                }catch (e: SocketTimeoutException){
                    Log.e("Error","${e.message}")
                }
            }
            profileListener.hideLoginButton()
            var image: Drawable
            CoroutineScope(Dispatchers.IO).launch {
                image = Repository.getDrawableByUrl(account.photoUrl.toString())
                withContext(Dispatchers.Main) {
                    profileListener.onSignIn(image,account.givenName.toString())
                }
            }
        } catch (e: ApiException) {
            Log.e(googleTag, "handleSignInResult:error", e)
        }
    }
}