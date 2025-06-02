package io.budge.homeapp.data.local

import android.content.Context

class PreferencesManagerImpl(context: Context) : PreferencesManager {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun isOnboardingComplete(): Boolean = prefs.getBoolean(KEY_ONBOARDING_DONE, false)

    override fun setOnboardingComplete(isComplete: Boolean) =
        prefs.edit().putBoolean(KEY_ONBOARDING_DONE, isComplete).apply()

    override fun getCurrentStep(): Int = prefs.getInt(KEY_CURRENT_STEP, 1)

    override fun setCurrentStep(step: Int) = prefs.edit().putInt(KEY_CURRENT_STEP, step).apply()

    override fun setLaunchedFromStepTwo() = prefs.edit().putBoolean(FROM_STEP_TWO, true).apply()

    override fun wasLaunchedFromStepTwo() = prefs.getBoolean(FROM_STEP_TWO, false)

    override fun resetLaunchedFromStepTwo() = prefs.edit().putBoolean(FROM_STEP_TWO, false).apply()

    companion object Keys {
        const val KEY_ONBOARDING_DONE = "onboarding_done"
        const val KEY_CURRENT_STEP = "onboarding_step"
        const val FROM_STEP_TWO= "from_step_two"
        const val PREFS_NAME = "home_app_prefs"
    }
}