package com.orange_infinity.onetrak_test.presenter

import android.annotation.SuppressLint
import android.view.View
import com.orange_infinity.onetrak_test.R
import com.orange_infinity.onetrak_test.data_layer.GoalPreferences
import com.orange_infinity.onetrak_test.entities.Step
import com.orange_infinity.onetrak_test.getDateFromTimeMilli
import kotlinx.android.synthetic.main.list_step.view.*

private const val DATE_FORMAT = "dd.MM.yyyy"

class StepActivityPresenter {

    private var stepsGoal = 0
    private lateinit var goalPreferences: GoalPreferences

    fun initStepView(stepView: View, step: Step) {
        goalPreferences = GoalPreferences()
        stepsGoal = goalPreferences.getGoalSteps(stepView.context)

        setStepsTextView(stepView, step)
        checkGoalComplete(stepView, step)
        setDate(stepView, step)
    }

    private fun checkGoalComplete(stepView: View, step: Step) {
        val achievedSteps = step.walk + step.aerobic + step.run
        if (achievedSteps < stepsGoal) {
            stepView.layout_goal_reached.visibility = View.GONE
        } else {
            stepView.layout_goal_reached.visibility = View.VISIBLE
            stepView.bottom_separator.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setStepsTextView(stepView: View, step: Step) {
        stepView.tv_walk_count.text = "${step.walk}"
        stepView.tv_aerobic_count.text = "${step.aerobic}"
        stepView.tv_run_count.text = "${step.run}"
        stepView.tv_count_of_steps.text =
            "${(step.walk + step.aerobic + step.run)} / $stepsGoal ${stepView.resources.getString(R.string.steps)}"
    }

    private fun setDate(stepView: View, step: Step) {
        stepView.tv_date.text = getDateFromTimeMilli(step.dateMilliseconds, DATE_FORMAT)
    }
}