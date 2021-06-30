package seoil.capstone.som_pos.data.network.model.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import seoil.capstone.som_pos.data.network.model.ShopRes

interface Shop {

    //매장 카테고리 요청
    @GET("shop/{shopId}/category")
    fun getShopCategory(
        @Path("shopId") shopId: String?
    ): Call<ShopRes>
}