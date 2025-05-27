package io.budge.homeapp.util

import android.content.Context
import io.budge.homeapp.util.Prefs.Keys.FROM_STEP_TWO
import io.budge.homeapp.util.Prefs.Keys.KEY_CURRENT_STEP
import io.budge.homeapp.util.Prefs.Keys.KEY_ONBOARDING_DONE
import io.budge.homeapp.util.Prefs.Keys.PREFS_NAME

object Prefs {

    fun isOnboardingComplete(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_ONBOARDING_DONE, false)
    }

    fun setOnboardingComplete(context: Context, isComplete: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_ONBOARDING_DONE, isComplete).apply()
    }

    fun getCurrentStep(context: Context): Int {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_CURRENT_STEP, 1)
    }

    fun setCurrentStep(context: Context, step: Int) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putInt(KEY_CURRENT_STEP, step).apply()
    }

    fun setLaunchedFromStepTwo(context: Context, value: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(FROM_STEP_TWO, value).apply()
    }

    fun wasLaunchedFromStepTwo(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(FROM_STEP_TWO, false)
    }

    fun resetLaunchedFromStepTwo(context: Context) {
        setLaunchedFromStepTwo(context, false)
    }

    object Keys {
        const val KEY_ONBOARDING_DONE = "onboarding_done"
        const val KEY_CURRENT_STEP = "onboarding_step"
        const val FROM_STEP_TWO= "from_step_two"
        const val PREFS_NAME = "home_app_prefs"
    }
}