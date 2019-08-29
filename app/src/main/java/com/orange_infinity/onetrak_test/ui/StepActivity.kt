package com.orange_infinity.onetrak_test.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

class StepActivity : AppCompatActivity(), StepNetworkListener {

    private var stepList = listOf<Step>()
    private val stepHandler = StepHandler()
    private val stepPresenter = StepActivityPresenter()

    private lateinit var stepRecyclerView: RecyclerView
    private lateinit var stepAdapter: StepAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)

        stepHandler.setSteps(this)
        setUpRecycler()
    }

    override fun onCompleteStepNetwork(stepList: List<Step>) {
        this.stepList = stepList
        updateUi()
    }

    private fun setUpRecycler() {
        stepAdapter = StepAdapter(stepList)
        stepRecyclerView = findViewById(R.id.layout_step_container)
        stepRecyclerView.layoutManager = LinearLayoutManager(this)
        stepRecyclerView.adapter = stepAdapter
    }

    private fun updateUi() {
        stepAdapter.setSteps(stepList)
        stepAdapter.notifyDataSetChanged()
    }

    private inner class StepHolder(val stepView: View) : RecyclerView.ViewHolder(stepView) {

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

    private inner class StepAdapter(private var stepList: List<Step>) : RecyclerView.Adapter<StepHolder>() {

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
