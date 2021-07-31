package seoil.capstone.som_pos.data.network.model.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import seoil.capstone.som_pos.data.network.model.GetCurrentRes


interface Point {

    @GET("point/current-point")
    fun getCurrentPoint(
            @Query("id") id: String?
    ): Call<GetCurrentRes>
}