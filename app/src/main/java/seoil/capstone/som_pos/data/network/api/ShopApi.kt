package seoil.capstone.som_pos.data.network.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.ShopRes
import seoil.capstone.som_pos.data.network.model.retrofit.Shop

class ShopApi(retrofit: Retrofit) {

    private val mShopInfo: Shop = retrofit.create(Shop::class.java)

    companion object {
        const val SUCCESS = 0
        const val ERROR = 1
        const val ERROR_UNDEFINED_VALUE = 2
        const val ERROR_NONE_DATE = 3
    }

    //매장 카테고리요청
    fun getShopCategory(shopId: String, onFinishApiListener: OnFinishApiListener<ShopRes>) {

        val call: Call<ShopRes> = mShopInfo.getShopCategory(shopId)
        call.enqueue(object: Callback<ShopRes> {
            override fun onFailure(call: Call<ShopRes>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<ShopRes>, response: Response<ShopRes>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {

                    return
                }
                onFinishApiListener.onSuccess(response.body()!!)
            }



        })
    }
}