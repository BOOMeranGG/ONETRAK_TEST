package com.orange_infinity.onetrak_test.data_layer.web

import com.orange_infinity.onetrak_test.entities.Step
import retrofit2.Call
import retrofit2.http.GET

interface JsonStepHolderApi {
    @GET("/intern/metric.json")
    fun getPost(): Call<List<Step>>
}