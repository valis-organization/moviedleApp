package moviedleapp.main.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moviedleapp.main.R
import moviedleapp.main.Repository


class ProfileFragment : Fragment() {

    private lateinit var idToken: String
    private val googleTag = "GOOGLE"
    private lateinit var loginButton: SignInButton
    private lateinit var logoutButton: Button
    private lateinit var profileIcon: ImageView
    private lateinit var profileName: TextView
    private lateinit var historyAndFavMoviesView: LinearLayout
    private lateinit var profileAndAchievementsView: LinearLayout


    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken("747713817851-sknc38jco126c6g11aiqjk5otbh1e28k.apps.googleusercontent.com")
        .build()
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val checkPermission =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.i(googleTag, "Successfully logged in")
                silentSignIn()
                loginButton.visibility = View.INVISIBLE
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assignViews()

        if (isSignedIn()) {
            loginButton.visibility = View.INVISIBLE
        }

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        loginButton.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                singIn()
            }

        }
        logoutButton.setOnClickListener {
            signOut()
        }
        silentSignIn()
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            idToken = account.idToken.toString()
            Log.i(googleTag, "id token")
            lifecycleScope.launch(Dispatchers.Main) {
                Repository.loginByGoogleToken(idToken)
            }
            var image: Drawable
            profileName.text = account.givenName
            CoroutineScope(Dispatchers.IO).launch {
                image = Repository.getDrawableByUrl(account.photoUrl.toString())
                withContext(Dispatchers.Main) {
                    profileIcon.setImageDrawable(image)
                    handleViewsOnLogin()
                }
            }

        } catch (e: ApiException) {
            Log.w(googleTag, "handleSignInResult:error", e)
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(requireActivity()) {
                Log.e("Google", "Successfully logged out")
            }
        handleViewsOnLogout()
    }

    private fun isSignedIn(): Boolean {
        return GoogleSignIn.getLastSignedInAccount(requireContext()) != null
    }

    private fun singIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        checkPermission.launch(signInIntent)
    }

    private fun silentSignIn() {
        mGoogleSignInClient.silentSignIn()
            .addOnCompleteListener(
                requireActivity()
            ) { task -> handleSignInResult(task) }
    }

    private fun assignViews() {
        loginButton = requireView().findViewById(R.id.singInButton)
        logoutButton = requireView().findViewById(R.id.signOutButton)
        profileIcon = requireView().findViewById(R.id.profile_image)
        profileName = requireView().findViewById(R.id.profile_name)
        historyAndFavMoviesView = requireView().findViewById(R.id.history_fav_view)
        profileAndAchievementsView = requireView().findViewById(R.id.profile_and_achievements_view)
    }

    private fun handleViewsOnLogin() {
        profileIcon.visibility = View.VISIBLE
        profileName.visibility = View.VISIBLE
        historyAndFavMoviesView.visibility = View.VISIBLE
        profileAndAchievementsView.visibility = View.VISIBLE
        logoutButton.visibility = View.VISIBLE
    }

    private fun handleViewsOnLogout() {
        loginButton.visibility = View.VISIBLE
        logoutButton.visibility = View.INVISIBLE
        profileIcon.visibility = View.INVISIBLE
        profileName.visibility = View.INVISIBLE
        historyAndFavMoviesView.visibility = View.INVISIBLE
        profileAndAchievementsView.visibility = View.INVISIBLE
        logoutButton.visibility = View.INVISIBLE
    }
}