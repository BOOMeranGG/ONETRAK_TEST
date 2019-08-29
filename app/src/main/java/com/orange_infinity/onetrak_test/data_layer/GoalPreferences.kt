package com.orange_infinity.onetrak_test.data_layer

import android.content.Context
import android.util.Log
import com.orange_infinity.onetrak_test.TAG

private const val FILE_SETTINGS_NAME = "settingsFileName"
private const val PREFERENCES_STEPS_GOAL_KEY = "stepsGoal"
private const val STANDARD_STEPS_GOAL = 4000

class GoalPreferences {

    fun getGoalSteps(context: Context): Int {
        val preferences = context.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        val steps = preferences.getInt(PREFERENCES_STEPS_GOAL_KEY, STANDARD_STEPS_GOAL)
        Log.i(TAG, "Preference return steps = $steps")
        return steps
    }

    fun setGoalSteps(context: Context, steps: Int) {
        val settings = context.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putInt(PREFERENCES_STEPS_GOAL_KEY, steps)
        editor.apply()
        Log.i(TAG, "Preference saving steps = $steps")
    }
}