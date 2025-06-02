package io.budge.homeapp.data.repository

import io.budge.homeapp.data.local.PreferencesManager
import io.budge.homeapp.domain.SettingsRepository
import io.budge.homeapp.platform.LauncherUtils
import io.budge.homeapp.core.Logger

class SettingsRepositoryImpl(
    private val preferencesManager: PreferencesManager,
    private val launcherUtils: LauncherUtils
) : SettingsRepository {

    override fun isOnboardingComplete() = preferencesManager.isOnboardingComplete()

    override fun setOnboardingComplete(isComplete: Boolean) = preferencesManager.setOnboardingComplete(isComplete)

    override fun getCurrentStep() = preferencesManager.getCurrentStep()

    override fun setCurrentStep(step: Int) = preferencesManager.setCurrentStep(step)

    override fun wasLaunchedFromStepTwo() = preferencesManager.wasLaunchedFromStepTwo()

    override fun setLaunchedFromStepTwo() = preferencesManager.setLaunchedFromStepTwo()

    override fun resetLaunchedFromStepTwo() = preferencesManager.resetLaunchedFromStepTwo()

    override fun shouldRedirectToOnboarding(): Boolean {
        val isOnboardingComplete = isOnboardingComplete()
        val isDefaultLauncher = launcherUtils.isAppDefaultLauncher()
        val launchedFromStepTwo = wasLaunchedFromStepTwo()

        if (!isOnboardingComplete && launchedFromStepTwo) {
            Logger.v(TAG, "Incomplete onboarding from step 2, show onboarding")
            return true
        }

        if (!isOnboardingComplete && !isDefaultLauncher) {
            Logger.v(TAG, "Incomplete onboarding and not default, show onboarding")
            return true
        }

        Logger.v(TAG, "Incomplete onboarding but not from step 2, stay")
        return false
    }

    override fun evaluateStartupStep(): Int {
        val currentStep = if (shouldJumpToStepThree()) 3 else getCurrentStep()
        return currentStep.coerceIn(1, 3)
    }

    override fun resetOnboardingIfNoLongerDefault() {
        if (isOnboardingComplete() && !launcherUtils.isAppDefaultLauncher()) {
            Logger.v(TAG, "Lost default launcher, resetting onboarding")
            setOnboardingComplete(false)
        }
    }

    private fun shouldJumpToStepThree(): Boolean =
        !isOnboardingComplete() && launcherUtils.isAppDefaultLauncher()

    companion object {
        private const val TAG = "SettingsRepositoryImpl"
    }
}