package seoil.capstone.som_pos.data.network.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.Status
import seoil.capstone.som_pos.data.network.model.StockModel
import seoil.capstone.som_pos.data.network.model.StockRes
import seoil.capstone.som_pos.data.network.model.StockUpdateNameModel
import seoil.capstone.som_pos.data.network.model.retrofit.Stock

class StockApi(retrofit: Retrofit) {

    private val mStockData = retrofit.create(Stock::class.java)

    companion object {
        const val SUCCESS = 0
        const val ERROR = 1
        const val ERROR_UNDEFINED_VALUE = 2
    }

    fun getStock(shopId: String, onFinishApiListener: OnFinishApiListener<StockRes>) {
        val call: Call<StockRes> = mStockData.getStock(shopId)
        call.enqueue(object : Callback<StockRes> {
            override fun onFailure(call: Call<StockRes>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<StockRes>, response: Response<StockRes>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }

    fun insertStock(req: StockModel, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mStockData.insertStock(req)
        call.enqueue(object : Callback<Status> {
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

    fun updateStock(req: StockUpdateNameModel, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mStockData.updateStock(req)
        call.enqueue(object : Callback<Status> {
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

    fun updateStockAmount(req: StockModel, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mStockData.updateStockAmount(req)
        call.enqueue(object : Callback<Status> {
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

    fun deleteStock(shopId: String, stockCode: Int, stockName: String, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mStockData.deleteStock(shopId, stockCode, stockName)
        call.enqueue(object : Callback<Status> {
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