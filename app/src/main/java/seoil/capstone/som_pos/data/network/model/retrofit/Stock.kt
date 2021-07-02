package seoil.capstone.som_pos.data.network.model.retrofit

import retrofit2.Call
import retrofit2.http.*
import seoil.capstone.som_pos.data.network.model.Status
import seoil.capstone.som_pos.data.network.model.StockModel
import seoil.capstone.som_pos.data.network.model.StockRes
import seoil.capstone.som_pos.data.network.model.StockUpdateNameModel

interface Stock {

    // 재고 정보 요청
    @GET("stock/{shopId}")
    fun getStock(
        @Path("shopId") shopId: String
    ): Call<StockRes>

    // 재고 추가 요청
    @POST("stock")
    fun insertStock(
        @Body req: StockModel
    ): Call<Status>

    // 재고 이름, 수량, 가격 수정 요청
    @PUT("stock")
    fun updateStock(
        @Body req: StockUpdateNameModel
    ): Call<Status>

    // 재고 수량 수정 요청
    @PUT("stock/amount")
    fun updateStockAmount(
        @Body req: StockModel
    ): Call<Status>

    // 재고 특정 매장의 특정 재고 삭제 요청
    @DELETE("stock/{shopId}")
    fun deleteStock(
        @Path("shopId") shopId: String,
        @Query("stockCode") stockCode: Int,
        @Query("stockName") stockName: String
    ): Call<Status>
}