package io.budge.homeapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import io.budge.homeapp.databinding.ActivityHomeBinding
import io.budge.homeapp.util.Logger
import io.budge.homeapp.util.OnboardingFlowState
import io.budge.homeapp.util.Prefs

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.v(TAG, "created")

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Logger.v(TAG, "Back pressed on Home — ignored")
            }
        })

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        Logger.v(TAG, "resumed")

        OnboardingFlowState.resetOnboardingIfNoLongerDefault(this)

        if (OnboardingFlowState.shouldRedirectToOnboarding(this)) {
            Logger.v(TAG, "Redirecting to onboarding from HomeActivity")
            Prefs.resetLaunchedFromStepTwo(this)
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }

    companion object {
        private const val TAG = "HomeActivity"
    }
}