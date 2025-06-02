package io.budge.homeapp.presentation.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.budge.homeapp.R
import io.budge.homeapp.data.local.PreferencesManagerImpl
import io.budge.homeapp.data.repository.SettingsRepositoryImpl
import io.budge.homeapp.util.LauncherUtilsImpl
import io.budge.homeapp.util.Logger

class OnboardingActivity : AppCompatActivity() {
    private val viewModel: OnboardingViewModel by viewModels {
        viewModelFactory {
            initializer {
                val prefs = PreferencesManagerImpl(applicationContext)
                val launcherUtils = LauncherUtilsImpl(applicationContext)
                val repository = SettingsRepositoryImpl(prefs, launcherUtils)
                val handle = createSavedStateHandle()
                OnboardingViewModel(handle, repository, launcherUtils)
            }
        }
    }

    private var lastShownStep: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.v(TAG, "created")
        setContentView(R.layout.activity_onboarding)

        if (viewModel.shouldFinishImmediately()) {
            Logger.v(TAG, "Already default launcher")
            finish()
            return
        }
        handleStepToShow()
    }

    override fun onResume() {
        super.onResume()
        handleStepToShow()
    }

    private fun handleStepToShow() {
        val currentStep = viewModel.evaluateStartupStep()
        Logger.v(TAG, "Loaded step: $currentStep")
        showStepFragment(currentStep)
    }

    fun goToNextStep() {
        viewModel.nextStepOrNull()?.let { nextStep ->
            Logger.v(TAG, "Moving to step $nextStep")
            viewModel.updateStep(nextStep)
            showStepFragment(nextStep)
        }
    }

    private fun showStepFragment(step: Int) {
        if (step == lastShownStep) {
            Logger.v(TAG, "Step $step already shown — skipping")
            return
        }
        val fragment = when (step) {
            1 -> StepOneFragment()
            2 -> StepTwoFragment()
            3 -> StepThreeFragment()
            else -> null
        }

        fragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, it)
                .commit()
            lastShownStep = step
        }
    }

    companion object {
        private const val TAG = "OnboardingActivity"
    }
}