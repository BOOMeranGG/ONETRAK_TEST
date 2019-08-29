package com.orange_infinity.onetrak_test.use_case

import android.util.Log
import com.orange_infinity.onetrak_test.TAG
import com.orange_infinity.onetrak_test.data_layer.web.StepNetworkService
import com.orange_infinity.onetrak_test.entities.Step
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StepHandler {

    fun setSteps(listener: StepNetworkListener) {
        Log.i(TAG, "Start work with network to create stepList")
        StepNetworkService.getInstance()
            .getJsonApi()
            .getPost()
            .enqueue(object : Callback<List<Step>> {
                override fun onResponse(call: Call<List<Step>>, response: Response<List<Step>>) {
                    val stepList = response.body()
                    if (stepList != null) {
                        listener.onCompleteStepNetwork(stepList)
                    } else {
                        Log.e(TAG, "StepHandler: smth went wrong, stepList is null")
                    }
                }

                override fun onFailure(call: Call<List<Step>>, t: Throwable) {
                    Log.e(TAG, "$call", t)
                }
            })
    }
}