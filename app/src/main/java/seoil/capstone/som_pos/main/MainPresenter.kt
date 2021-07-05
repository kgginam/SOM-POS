package seoil.capstone.som_pos.main

import android.util.Log
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.api.ShopApi
import seoil.capstone.som_pos.data.network.model.ShopRes

class MainPresenter: MainContract.Presenter {

    private var mView: MainContract.View? = null
    private var mInteractor: MainInteractor? = null

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

                    Log.d("MainPresenter", t.toString())
                    if (t.status == ShopApi.SUCCESS) {

                        mView!!.setGlobalData(t.results[0].shopCategory.toString())
                        Log.d("MainPresenter", t.results[0].shopCategory.toString())
                    } else if (t.status == ShopApi.ERROR) {

                        mView!!.showDialog("서버 오류 입니다. 다시 시도해주세요")
                    }
                }

                override fun onFailure(t: Throwable?) {
                    mView!!.showDialog("서버 오류 입니다. 관리자에게 문의해주세요\n" + t.toString())
                }

            }

        mInteractor!!.getShopInformation(shopId, callback)
    }
}