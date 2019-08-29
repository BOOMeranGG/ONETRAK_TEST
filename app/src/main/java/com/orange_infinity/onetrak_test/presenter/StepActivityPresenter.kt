package com.orange_infinity.onetrak_test.presenter

import android.annotation.SuppressLint
import android.view.View
import com.orange_infinity.onetrak_test.R
import com.orange_infinity.onetrak_test.data_layer.GoalPreferences
import com.orange_infinity.onetrak_test.entities.Step
import com.orange_infinity.onetrak_test.getDateFromTimeMilli
import kotlinx.android.synthetic.main.list_step.view.*
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.orange_infinity.onetrak_test.convertToPx

private const val DATE_FORMAT = "dd.MM.yyyy"
private const val MINIMUM_PERCENT = 0.02f
private const val MAXIMUM_PERCENT = 0.8f

class StepActivityPresenter {

    private var stepsGoal = 0
    private lateinit var goalPreferences: GoalPreferences

    fun initStepView(stepView: View, step: Step) {
        goalPreferences = GoalPreferences()
        stepsGoal = goalPreferences.getGoalSteps(stepView.context)

        setStepsTextView(stepView, step)
        checkGoalComplete(stepView, step)
        setProgressBar(stepView, step)
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

    private fun setProgressBar(stepView: View, step: Step) {
        val totalSteps = step.walk + step.aerobic + step.run

        stepView.progress_bar_walk.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            convertToPx(10, stepView.resources),
            step.walk.toFloat() / totalSteps
        ).also {
            it.marginEnd = convertToPx(1, stepView.resources)
            it.marginStart = convertToPx(20, stepView.resources)
        }
        stepView.progress_bar_aerobic.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            convertToPx(10, stepView.resources),
            step.aerobic.toFloat() / totalSteps
        ).also {
            it.marginStart = convertToPx(1, stepView.resources)
            it.marginEnd = convertToPx(1, stepView.resources)
        }
        stepView.progress_bar_run.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            convertToPx(10, stepView.resources),
            step.run.toFloat() / totalSteps
        ).also {
            it.marginStart = convertToPx(1, stepView.resources)
            it.marginEnd = convertToPx(20, stepView.resources)
        }
    }
}