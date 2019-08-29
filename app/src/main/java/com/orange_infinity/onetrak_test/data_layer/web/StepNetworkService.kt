package com.orange_infinity.onetrak_test.data_layer.web

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val STEP_URL = "https://intern-f6251.firebaseio.com"

class StepNetworkService private constructor() {

    private var retrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
        retrofit = Retrofit.Builder()
            .baseUrl(STEP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    companion object {
        private val instance: StepNetworkService? = null

        fun getInstance(): StepNetworkService {
            return instance ?: StepNetworkService()
        }
    }

    fun getJsonApi(): JsonStepHolderApi {
        return retrofit.create(JsonStepHolderApi::class.java)
    }
}