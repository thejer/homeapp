package io.budge.homeapp.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.budge.homeapp.R
import io.budge.homeapp.ui.OnboardingViewModel
import io.budge.homeapp.ui.fragments.StepOneFragment
import io.budge.homeapp.ui.fragments.StepThreeFragment
import io.budge.homeapp.ui.fragments.StepTwoFragment
import io.budge.homeapp.util.Logger

class OnboardingActivity : AppCompatActivity() {
    private val viewModel: OnboardingViewModel by viewModels {
        viewModelFactory {
            initializer {
                val handle = createSavedStateHandle()
                OnboardingViewModel(handle)
            }
        }
    }

    private var lastShownStep: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.v(TAG,"created")
        setContentView(R.layout.activity_onboarding)

        if (viewModel.shouldFinishImmediately(this)) {
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
        val currentStep = viewModel.evaluateStartupStep(this)
        Logger.v(TAG, "Loaded step: $currentStep")
        showStepFragment(currentStep)
    }

    fun goToNextStep() {
        viewModel.nextStepOrNull()?.let { nextStep ->
            Logger.v(TAG, "Moving to step $nextStep")
            viewModel.updateStep(this, nextStep)
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