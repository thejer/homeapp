package io.budge.homeapp.util

import android.content.Context
import io.budge.homeapp.util.Prefs.isOnboardingComplete

object OnboardingFlowState {

    fun shouldRedirectToOnboarding(context: Context): Boolean {
        return !isOnboardingComplete(context) &&
               Prefs.wasLaunchedFromStepTwo(context)
    }

    private fun shouldJumpToStepThree(context: Context): Boolean =
        !isOnboardingComplete(context) && LauncherUtils.isAppDefaultLauncher(context)

    fun evaluateStartupStep(context: Context): Int {
        val currentStep = if (shouldJumpToStepThree(context)) 3 else Prefs.getCurrentStep(context)
        return currentStep.coerceIn(1, 3)
    }
}