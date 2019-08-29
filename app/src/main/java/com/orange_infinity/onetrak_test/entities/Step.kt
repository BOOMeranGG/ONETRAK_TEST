package com.orange_infinity.onetrak_test.entities

import com.google.gson.annotations.SerializedName

class Step {
    @SerializedName("walk")
    var walk: Int = 0
    @SerializedName("aerobic")
    var aerobic: Int = 0
    @SerializedName("run")
    var run: Int = 0
    @SerializedName("date")
    var dateMilliseconds: Long = 0
}
