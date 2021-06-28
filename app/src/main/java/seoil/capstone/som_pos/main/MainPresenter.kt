package seoil.capstone.som_pos.main

import android.util.Log
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.api.ShopApi
import seoil.capstone.som_pos.data.network.model.ShopRes

class MainPresenter : MainContract.Presenter {

    private var mView: MainContract.View?= null
    private var mInteractor : MainInteractor?= null

    override fun setView(view: MainContract.View) {
        mView = view
    }

    override fun releaseView() {
        mView = null
    }

    override fun createInteractor() {
        mInteractor = MainInteractor()
    }

    override fun releaseInteractor() {
        mInteractor = null
    }

    fun getShopInformation(shopId : String) {

        val callback : OnFinishApiListener<ShopRes> =
            object : OnFinishApiListener<ShopRes> {
                override fun onSuccess(t: ShopRes) {

                    if (t.status == ShopApi.SUCCESS) {

                        mView!!.setGlobalData(t.shopCategory.toString())
                    }
                }

                override fun onFailure(t: Throwable?) {
                    Log.d("MainError", t.toString())
                }

            }

        mInteractor!!.getShopInformation(shopId, callback)
    }
}