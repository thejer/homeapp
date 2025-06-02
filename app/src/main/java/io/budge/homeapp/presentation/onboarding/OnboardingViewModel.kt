package io.budge.homeapp.presentation.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.budge.homeapp.domain.SettingsRepository
import io.budge.homeapp.util.LauncherUtils
import io.budge.homeapp.util.Logger

class OnboardingViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: SettingsRepository,
    private val launcherUtils: LauncherUtils,
) : ViewModel() {

    private var fallbackStep: Int = 1

    private var currentStep: Int
        get() = savedStateHandle.get<Int>("step") ?: fallbackStep
        set(value) {
            Logger.v(TAG, "Setting step to $value")
            savedStateHandle["step"] = value
        }

    fun markComplete() {
        Logger.v(TAG, "Marking onboarding complete")
        repository.setOnboardingComplete(true)
        saveToPrefs(1)
    }

    fun shouldFinishImmediately(): Boolean = repository.isOnboardingComplete() && launcherUtils.isAppDefaultLauncher()

    fun nextStepOrNull(): Int? = if (currentStep < 3) currentStep + 1 else null

    private fun saveToPrefs(step: Int) {
        repository.setCurrentStep(step)
    }

    fun evaluateStartupStep(): Int {
        fallbackStep = repository.getCurrentStep()
        val step = repository.evaluateStartupStep()
        updateStep(step)
        return step
    }

    fun updateStep(step: Int) {
        Logger.v(TAG, "Setting step via setStep($step)")
        currentStep = step
        saveToPrefs(step)
    }

    fun resetLaunchedFromStepTwo() = repository.resetLaunchedFromStepTwo()

    fun setLaunchedFromStepTwo() = repository.setLaunchedFromStepTwo()

    fun isAppDefaultLauncher() = launcherUtils.isAppDefaultLauncher()

    companion object {
        private const val TAG = "OnboardingViewModel"
    }
}