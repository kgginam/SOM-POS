package seoil.capstone.som_pos.data.network.model.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import seoil.capstone.som_pos.data.network.model.PaymentModel
import seoil.capstone.som_pos.data.network.model.PaymentRes
import seoil.capstone.som_pos.data.network.model.Status

interface Payment {

    @GET("payment/{memberId}")
    fun getPayment(
        @Path("memberId") memberId: String
    ): Call<PaymentRes>

    @POST("payment/pay")
    fun pay(
        @Body req: PaymentModel
    ): Call<Status>

    @POST("payment/cancel")
    fun cancel(
        @Body req: PaymentModel
    ): Call<Status>
}