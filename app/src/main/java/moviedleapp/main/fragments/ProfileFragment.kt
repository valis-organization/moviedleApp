package moviedleapp.main.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import moviedleapp.main.R
import moviedleapp.main.Repository


class ProfileFragment : Fragment() {

    private lateinit var idToken: String
    private val googleTag = "GOOGLE"
    private lateinit var loginButton : SignInButton

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
        loginButton = requireView().findViewById(R.id.singInButton)
        val logoutButton: Button = requireView().findViewById(R.id.signOutButton)

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
            loginButton.visibility = View.VISIBLE
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
        } catch (e: ApiException) {
            Log.w(googleTag, "handleSignInResult:error", e)
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(requireActivity()) {
                Log.e("Google", "Successfully logged out")
            }
    }

    private fun isSignedIn(): Boolean {
        return GoogleSignIn.getLastSignedInAccount(requireContext()) != null
    }

    private fun singIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        checkPermission.launch(signInIntent)
    }
    private fun silentSignIn(){
        mGoogleSignInClient.silentSignIn()
            .addOnCompleteListener(
                requireActivity()
            ) { task -> handleSignInResult(task) }
    }
}