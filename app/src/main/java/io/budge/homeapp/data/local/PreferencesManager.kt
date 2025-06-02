package io.budge.homeapp.data.local

interface PreferencesManager {
    fun isOnboardingComplete(): Boolean
    fun setOnboardingComplete(isComplete: Boolean)
    fun getCurrentStep(): Int
    fun setCurrentStep(step: Int)
    fun wasLaunchedFromStepTwo(): Boolean
    fun setLaunchedFromStepTwo()
    fun resetLaunchedFromStepTwo()
}