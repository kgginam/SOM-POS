package seoil.capstone.som_pos.ui.sell

import android.util.Log
import seoil.capstone.som_pos.data.model.DataModel
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.api.MenuApi
import seoil.capstone.som_pos.data.network.model.MenuRes

class SellManagementPresenter: SellManagementContract.Presenter{

    private var mView: SellManagementContract.View? = null
    private var mInteractor: SellManagementInteractor? = null

    override fun setView(view: SellManagementContract.View) {
        mView = view
    }

    override fun releaseView() {
        mView = null
    }

    override fun createInteractor() {
        mInteractor = SellManagementInteractor()
    }

    override fun releaseInteractor() {
        mInteractor = null
    }
    override fun initTotalPrice(countList: ArrayList<Int>) {
        mView!!.initTotalPrice(countList)
    }


    fun getMenuInfo(shopId: String) {

        val callback: OnFinishApiListener<MenuRes> =
                object: OnFinishApiListener<MenuRes> {
                    override fun onSuccess(t: MenuRes) {

                        if (t.status == MenuApi.SUCCESS) {

                            Log.d("getMenu", t.toString())
                            val results: ArrayList<DataModel.MenuData> = ArrayList()

                            for (i in t.results!!.indices) {

                                results.add(
                                        DataModel.MenuData(
                                                t.results[i].menuCode,
                                                t.results[i].menuName.toString(),
                                                t.results[i].menuIngredients.toString(),
                                                t.results[i].menuPrice
                                        )
                                )
                            }

                            mView!!.setMenuInfo(results)
                        } else if (t.status == MenuApi.ERROR_NONE_DATA) {

                            val results: ArrayList<DataModel.MenuData> = ArrayList()
                            mView!!.setMenuInfo(results)
                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("menu", t.toString())
                    }
                }

        mInteractor!!.getMenuInfo(shopId, callback)
    }
}