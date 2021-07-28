package seoil.capstone.som_pos.data.network.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.PaymentModel
import seoil.capstone.som_pos.data.network.model.PaymentRes
import seoil.capstone.som_pos.data.network.model.Status
import seoil.capstone.som_pos.data.network.model.retrofit.Payment

class PaymentApi(retrofit: Retrofit) {

    private val mPaymentInfo: Payment = retrofit.create(Payment::class.java)

    companion object {
        const val SUCCESS = 0
        const val ERROR = 1
        const val ERROR_UNDEFINED_VALUE = 2
        const val ERROR_NONE_DATA = 3
    }

    // 결제내역 요청
    fun getPayment(memberId: String, onFinishApiListener: OnFinishApiListener<PaymentRes>) {

        val call: Call<PaymentRes> = mPaymentInfo.getPayment(memberId)
        call.enqueue(object: Callback<PaymentRes> {
            override fun onFailure(call: Call<PaymentRes>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<PaymentRes>, response: Response<PaymentRes>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {

                    return
                }
                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }

    // 결제(코드와 날짜 제외한 모델)
    fun pay(req: PaymentModel, onFinishApiListener: OnFinishApiListener<Status>) {

        val call: Call<Status> = mPaymentInfo.pay(req)
        call.enqueue(object: Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {

                    return
                }
                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }

    // 결제 취소(모델 내의 모든 데이터 필요)
    fun cancel(req: PaymentModel, onFinishApiListener: OnFinishApiListener<Status>) {

        val call: Call<Status> = mPaymentInfo.cancel(req)
        call.enqueue(object: Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {

                    return
                }
                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }
}