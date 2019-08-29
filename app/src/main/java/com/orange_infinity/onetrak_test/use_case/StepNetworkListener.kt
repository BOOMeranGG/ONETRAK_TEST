package com.orange_infinity.onetrak_test.use_case

import com.orange_infinity.onetrak_test.entities.Step

interface StepNetworkListener {

    fun onCompleteStepNetwork(stepList: List<Step>)
}