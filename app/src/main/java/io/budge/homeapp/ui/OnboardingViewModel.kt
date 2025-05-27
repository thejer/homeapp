package io.budge.homeapp.ui

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.budge.homeapp.util.LauncherUtils
import io.budge.homeapp.util.Logger
import io.budge.homeapp.util.OnboardingFlowState
import io.budge.homeapp.util.Prefs

class OnboardingViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var fallbackStep: Int = 1

    private var currentStep: Int
        get() = savedStateHandle.get<Int>("step") ?: fallbackStep
        set(value) {
            Logger.v(TAG, "Setting step to $value")
            savedStateHandle["step"] = value
        }

    fun markComplete(context: Context) {
        Logger.v(TAG, "Marking onboarding complete")
        Prefs.setOnboardingComplete(context)
        saveToPrefs(context, 1)
    }

    private fun isOnboardingComplete(context: Context): Boolean {
        return Prefs.isOnboardingComplete(context)
    }

    fun shouldFinishImmediately(context: Context): Boolean {
        return isOnboardingComplete(context) &&
                LauncherUtils.isAppDefaultLauncher(context)
    }

    fun nextStepOrNull(): Int? = if (currentStep < 3) currentStep + 1 else null

    private fun loadFromPrefs(context: Context): Int {
        return Prefs.getCurrentStep(context)
    }

    private fun saveToPrefs(context: Context, step: Int) {
        Prefs.setCurrentStep(context, step)
    }

    fun evaluateStartupStep(context: Context): Int {
        fallbackStep = loadFromPrefs(context) // populate fallback if savedStateHandle is empty
        val step = OnboardingFlowState.evaluateStartupStep(context)
        updateStep(context, step)
        return step
    }

    fun updateStep(context: Context, step: Int) {
        Logger.v(TAG, "Setting step via setStep($step)")
        currentStep = step
        saveToPrefs(context, step)
    }

    companion object {
        private const val TAG = "OnboardingViewModel"
    }
}