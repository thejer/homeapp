package io.budge.homeapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import io.budge.homeapp.util.LauncherUtils
import io.budge.homeapp.util.Logger
import io.budge.homeapp.util.OnboardingFlowState
import io.budge.homeapp.util.Prefs

class OnboardingViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    var currentStep: Int
        get() = savedStateHandle.get<Int>("step") ?: loadFromPrefs()
        set(value) {
            Logger.v(TAG, "Setting step to $value")
            savedStateHandle["step"] = value
            saveToPrefs(value)
        }

    fun markComplete() {
        Logger.v(TAG, "Marking onboarding complete")
        Prefs.setOnboardingComplete(getApplication())
        saveToPrefs(1)
    }

    private fun isOnboardingComplete(): Boolean = Prefs.isOnboardingComplete(getApplication())

    fun shouldFinishImmediately(): Boolean =
        isOnboardingComplete() && LauncherUtils.isAppDefaultLauncher(getApplication())


    private fun loadFromPrefs(): Int = Prefs.getCurrentStep(getApplication())

    private fun saveToPrefs(step: Int) {
        Prefs.setCurrentStep(getApplication(), step)
    }

    fun nextStepOrNull(): Int? = if (currentStep < 3) currentStep + 1 else null

    fun evaluateStartupStep(): Int {
        val step = OnboardingFlowState.evaluateStartupStep(getApplication())
        currentStep = step
        return step
    }

    companion object {
        private const val TAG = "OnboardingViewModel"
    }
}