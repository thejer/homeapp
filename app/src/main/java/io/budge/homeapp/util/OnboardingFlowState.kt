package io.budge.homeapp.util

import android.content.Context
import io.budge.homeapp.util.Prefs.isOnboardingComplete

object OnboardingFlowState {

    fun shouldRedirectToOnboarding(context: Context): Boolean {
        val isOnboardingComplete = isOnboardingComplete(context)
        val isDefaultLauncher = LauncherUtils.isAppDefaultLauncher(context)
        val launchedFromStepTwo = Prefs.wasLaunchedFromStepTwo(context)

        if (!isOnboardingComplete && launchedFromStepTwo) {
            Logger.v("OnboardingFlowState", "Incomplete onboarding from step 2 — show onboarding")
            return true
        }

        if (!isOnboardingComplete && !isDefaultLauncher) {
            Logger.v("OnboardingFlowState", "Incomplete onboarding and not default — show onboarding")
            return true
        }

        Logger.v("OnboardingFlowState", "Incomplete onboarding but not from step 2 — stay")
        return false
    }


    private fun shouldJumpToStepThree(context: Context): Boolean =
        !isOnboardingComplete(context) && LauncherUtils.isAppDefaultLauncher(context)

    fun evaluateStartupStep(context: Context): Int {
        val currentStep = if (shouldJumpToStepThree(context)) 3 else Prefs.getCurrentStep(context)
        return currentStep.coerceIn(1, 3)
    }

    fun resetOnboardingIfNoLongerDefault(context: Context) {
        val isOnboardingComplete = isOnboardingComplete(context)
        val isDefaultLauncher = LauncherUtils.isAppDefaultLauncher(context)

        if (isOnboardingComplete && !isDefaultLauncher) {
            Logger.v("OnboardingFlowState", "Lost default launcher, resetting onboarding")
            Prefs.setOnboardingComplete(context, false)
        }
    }
}