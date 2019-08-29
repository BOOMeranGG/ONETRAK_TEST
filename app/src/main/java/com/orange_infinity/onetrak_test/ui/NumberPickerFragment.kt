package com.orange_infinity.onetrak_test.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.orange_infinity.onetrak_test.R
import com.orange_infinity.onetrak_test.TAG
import com.orange_infinity.onetrak_test.data_layer.GoalPreferences
import com.shawnlin.numberpicker.NumberPicker

class NumberPickerFragment(private val callbacks: DialogCallback) : androidx.fragment.app.DialogFragment() {

    private lateinit var pickerList: List<NumberPicker>
    private val goalPreferences = GoalPreferences()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_number_picker, null)
        Log.i(TAG, "Create NumberPickerFragment")
        pickerList = listOf(
            view.findViewById(R.id.numberPicker1), view.findViewById(R.id.numberPicker2),
            view.findViewById(R.id.numberPicker3), view.findViewById(R.id.numberPicker4),
            view.findViewById(R.id.numberPicker5)
        )
        initCurrentGoal()

        return AlertDialog.Builder(activity)
            .setView(view)
            .setTitle(view.resources.getString(R.string.choose_goal))
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->
                confirmGoal()
            })
            .create()
    }

    private fun initCurrentGoal() {
        var currentGoal = goalPreferences.getGoalSteps(context!!)
        var multiplier = 10000

        for (i in 0 until pickerList.size) {
            pickerList[i].value = currentGoal / multiplier
            currentGoal -= pickerList[i].value * multiplier
            multiplier /= 10
        }
    }

    private fun confirmGoal() {
        var newGoal = 0
        var multiplier = 10000

        for (i in 0 until pickerList.size) {
            newGoal += pickerList[i].value * multiplier
            multiplier /= 10
        }
        goalPreferences.setGoalSteps(context ?: return, newGoal)
        callbacks.goalUpdated()
    }

    interface DialogCallback {
        fun goalUpdated()
    }
}