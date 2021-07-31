package seoil.capstone.som_pos.data.network.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class GetCurrentRes (
    @SerializedName("status")
    @Expose
    val status: Int,

    @SerializedName("point")
    @Expose
    var point: Int
)