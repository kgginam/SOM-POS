package seoil.capstone.som_pos.data.network.api

import retrofit2.*
import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.GetCurrentRes
import seoil.capstone.som_pos.data.network.model.retrofit.Point


class PointApi(retrofit: Retrofit) {

    private val mPointData: Point = retrofit.create(Point::class.java)

    companion object {

        // 포인트 응답 코드
        const val SUCCESS = 0
        const val ERROR = 1
        const val ERROR_UNDEFINED_VALUE = 2
        const val ERROR_NONE_DATA = 3
        const val ERROR_NOT_ENOUGH_POINT = 4
    }

    // 잔여 포인트 요청
    fun getCurrentPoint(id: String, onFinishApiListener: OnFinishApiListener<GetCurrentRes>) {
        val call: Call<GetCurrentRes> = mPointData.getCurrentPoint(id)

        call.enqueue(object : Callback<GetCurrentRes?> {
            override fun onResponse(call: Call<GetCurrentRes?>?, response: Response<GetCurrentRes?>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {

                    return
                }
                onFinishApiListener.onSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<GetCurrentRes?>?, t: Throwable?) {
                onFinishApiListener.onFailure(t)
            }
        })
    }
}