package io.budge.homeapp.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import io.budge.homeapp.core.Logger
import io.budge.homeapp.data.local.PreferencesManagerImpl
import io.budge.homeapp.data.repository.SettingsRepositoryImpl
import io.budge.homeapp.databinding.ActivityHomeBinding
import io.budge.homeapp.platform.LauncherUtilsImpl
import io.budge.homeapp.presentation.onboarding.ui.OnboardingActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val prefs by lazy { PreferencesManagerImpl(applicationContext) }
    private val launcherUtils by lazy { LauncherUtilsImpl(applicationContext) }
    private val repository by lazy { SettingsRepositoryImpl(prefs, launcherUtils) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.v(TAG, "created")
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Logger.v(TAG, "Back pressed on Home, ignored")
            }
        })

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        Logger.v(TAG, "resumed")
        repository.resetOnboardingIfNoLongerDefault()
        if (repository.shouldRedirectToOnboarding()) {
            Logger.v(TAG, "Redirecting to onboarding from HomeActivity")
            prefs.resetLaunchedFromStepTwo()
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }

    companion object {
        private const val TAG = "HomeActivity"
    }
}