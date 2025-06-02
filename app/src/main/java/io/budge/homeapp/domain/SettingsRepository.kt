package io.budge.homeapp.domain

interface SettingsRepository {
    fun isOnboardingComplete(): Boolean
    fun setOnboardingComplete(isComplete: Boolean)
    fun getCurrentStep(): Int
    fun setCurrentStep(step: Int)
    fun wasLaunchedFromStepTwo(): Boolean
    fun setLaunchedFromStepTwo()
    fun resetLaunchedFromStepTwo()
    fun shouldRedirectToOnboarding(): Boolean
    fun evaluateStartupStep(): Int
    fun resetOnboardingIfNoLongerDefault()
}