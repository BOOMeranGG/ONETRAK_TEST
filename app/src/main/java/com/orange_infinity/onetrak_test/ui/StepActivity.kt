package com.orange_infinity.onetrak_test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.orange_infinity.onetrak_test.R
import com.orange_infinity.onetrak_test.convertToPx
import com.orange_infinity.onetrak_test.entities.Step
import com.orange_infinity.onetrak_test.presenter.StepActivityPresenter
import com.orange_infinity.onetrak_test.use_case.StepHandler
import com.orange_infinity.onetrak_test.use_case.StepNetworkListener
import kotlinx.android.synthetic.main.activity_step.*
import android.view.animation.AnimationUtils
import android.view.animation.Animation

private const val DIALOG_TAG = "dialog_tag"

class StepActivity : AppCompatActivity(), StepNetworkListener, NumberPickerFragment.DialogCallback {

    private var stepList = listOf<Step>()
    private val stepHandler = StepHandler()
    private val stepPresenter = StepActivityPresenter()

    private lateinit var stepRecyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var stepAdapter: StepAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)
        stepHandler.setSteps(this)
        setUpRecycler()

        img_set_goal.setOnClickListener {
            val dialog = NumberPickerFragment(this)
            dialog.show(supportFragmentManager, DIALOG_TAG)
        }
    }

    override fun onCompleteStepNetwork(stepList: List<Step>) {
        this.stepList = stepList
        val fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_animation)
        img_load.startAnimation(fadeAnimation)
        fadeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                layout_step_container.visibility = View.VISIBLE
                val appearAnimation = AnimationUtils.loadAnimation(this@StepActivity, R.anim.appear_animation)
                layout_step_container.startAnimation(appearAnimation)
                updateUi()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    override fun goalUpdated() {
        updateUi()
    }

    private fun setUpRecycler() {
        stepAdapter = StepAdapter(stepList)
        stepRecyclerView = findViewById(R.id.layout_step_container)
        stepRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        stepRecyclerView.adapter = stepAdapter
    }

    private fun updateUi() {
        stepAdapter.setSteps(stepList)
        stepAdapter.notifyDataSetChanged()
    }

    private inner class StepHolder(val stepView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(stepView) {

        fun bindStep(step: Step) {
            if (adapterPosition == 0) {
                val layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.topMargin = convertToPx(50, resources)
                layoutParams.bottomMargin = convertToPx(20, resources)
                stepView.layoutParams = layoutParams
            }
            stepPresenter.initStepView(stepView, step)
        }
    }

    private inner class StepAdapter(private var stepList: List<Step>) : androidx.recyclerview.widget.RecyclerView.Adapter<StepHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
            val layoutInflater = LayoutInflater.from(this@StepActivity)
            val view = layoutInflater.inflate(R.layout.list_step, parent, false)

            return StepHolder(view)
        }

        override fun onBindViewHolder(holder: StepHolder, position: Int) {
            val step = stepList[position]
            holder.bindStep(step)
        }

        override fun getItemCount(): Int = stepList.size

        fun setSteps(stepList: List<Step>) {
            this.stepList = stepList
        }
    }
}
