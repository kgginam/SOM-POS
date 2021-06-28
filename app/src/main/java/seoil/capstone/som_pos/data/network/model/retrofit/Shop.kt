package seoil.capstone.som_pos.data.network.model.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import seoil.capstone.som_pos.data.network.model.ShopRes

interface Shop {
    @GET("shop/{shopId}/information")
    fun getShopInformation(
        @Path("shopId") shopId: String?
    ): Call<ShopRes>?
}