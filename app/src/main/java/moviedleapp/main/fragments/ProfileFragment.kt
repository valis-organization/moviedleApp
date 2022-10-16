package moviedleapp.main.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moviedleapp.main.R
import moviedleapp.main.controllers.ProfileFragmentController
import moviedleapp.main.controllers.ProfileListener


class ProfileFragment : Fragment() {

    //VIEWS
    //login
    private lateinit var loginButton: SignInButton
    private lateinit var logoutButton: Button
    //profile
    private lateinit var profileIcon: ImageView
    private lateinit var profileName: TextView
    //card views
    private lateinit var historyAndFavMoviesView: LinearLayout
    private lateinit var profileAndAchievementsView: LinearLayout
    private lateinit var matchHistory: CardView
    private lateinit var favMovies: CardView
    private lateinit var profileInfo: CardView
    private lateinit var achievements: CardView
    //controller
    private lateinit var controller: ProfileFragmentController

    private val googleLogInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                controller.handleSignInResult(task)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assignViews()
        setCardViewOnClickListeners()
        controller = ProfileFragmentController(
            object : ProfileListener {
                override fun hideLoginButton() {
                    loginButton.visibility = View.INVISIBLE
                }

                override fun onSignIn(image: Drawable,name :String ) {
                    profileIcon.setImageDrawable(image)
                    profileName.text = name
                    handleViewsOnLogin()
                }

                override fun getFragmentActivity(): FragmentActivity {
                    return requireActivity()
                }

                override fun getActivityResultLauncher(): ActivityResultLauncher<Intent> {
                    return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
                }

                override fun onLogout() {
                    handleViewsOnLogout()
                }
            },
            googleLogInLauncher
        )

        setButtonsListeners()
        controller.silentSignIn()
    }

    private fun assignViews() {
        loginButton = requireView().findViewById(R.id.singInButton)
        logoutButton = requireView().findViewById(R.id.signOutButton)
        profileIcon = requireView().findViewById(R.id.profile_image)
        profileName = requireView().findViewById(R.id.profile_name)
        historyAndFavMoviesView = requireView().findViewById(R.id.history_fav_view)
        profileAndAchievementsView = requireView().findViewById(R.id.profile_and_achievements_view)
        matchHistory = requireView().findViewById(R.id.match_history)
        favMovies = requireView().findViewById(R.id.fav_movies)
        achievements = requireView().findViewById(R.id.achievements)
        profileInfo = requireView().findViewById(R.id.profile_info)
    }

    private fun setCardViewOnClickListeners() {
        matchHistory.setOnClickListener {
            Toast.makeText(this.requireContext(), "Coming soon!", Toast.LENGTH_SHORT).show()
        }
        favMovies.setOnClickListener {
            Toast.makeText(this.requireContext(), "Coming soon!", Toast.LENGTH_SHORT).show()
        }
        achievements.setOnClickListener {
            Toast.makeText(this.requireContext(), "Coming soon!", Toast.LENGTH_SHORT).show()
        }
        profileInfo.setOnClickListener {
            Toast.makeText(this.requireContext(), "Coming soon!", Toast.LENGTH_SHORT).show()
        }
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

    private fun setButtonsListeners() {
        loginButton.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                controller.singIn()
            }
        }
        logoutButton.setOnClickListener {
            controller.signOut()
        }
    }
}