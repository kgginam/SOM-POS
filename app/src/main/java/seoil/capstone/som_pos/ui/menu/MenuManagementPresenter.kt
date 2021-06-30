package seoil.capstone.som_pos.ui.menu

import android.util.Log
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.api.MenuApi
import seoil.capstone.som_pos.data.network.api.StockApi
import seoil.capstone.som_pos.data.network.model.MenuRes
import seoil.capstone.som_pos.data.network.model.Status
import seoil.capstone.som_pos.data.network.model.StockModel
import seoil.capstone.som_pos.data.network.model.StockRes

class MenuManagementPresenter: MenuManagementContract.Presenter {

    private var mView: MenuManagementContract.View?= null
    private var mInteractor: MenuManagementInteractor?= null

    override fun setView(view: MenuManagementContract.View) {
        mView = view
    }

    override fun releaseView() {
        mView = null
    }

    override fun createInteractor() {
        mInteractor = MenuManagementInteractor()
    }

    override fun releaseInteractor() {
        mInteractor = null
    }

    fun isTextSet(str: String?): Boolean {
        if (str == null || str == "" || str.isEmpty()) {

            return false
        }
        return true
    }

    fun isNumeric(str: String): Boolean {
        for (i in str.indices) {

            if (str[i] < '0' || str[i] > '9') {

                return false
            }
        }
        return true
    }

    fun getMenuInfo(shopId: String) {

        val callback: OnFinishApiListener<MenuRes> =
                object: OnFinishApiListener<MenuRes> {
                    override fun onSuccess(t: MenuRes) {

                        if (t.status == MenuApi.SUCCESS) {

                            val results: ArrayList<MenuManagementActivity.MenuData> = ArrayList()

                            for (i in t.results!!.indices) {

                                val temp: MenuManagementActivity.MenuData = MenuManagementActivity.MenuData(

                                        t.results[i].menuName.toString(),
                                        t.results[i].menuIngredients.toString(),
                                        t.results[i].menuPrice!!
                                )

                                results.add(temp)
                            }

                            mView!!.setMenuInfo(results)
                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("menu", t.toString())
                    }
                }

        mInteractor!!.getMenuInfo(shopId, callback)
    }

    fun getStock(shopId: String) {

        val callback: OnFinishApiListener<StockRes> =
                object: OnFinishApiListener<StockRes> {
                    override fun onSuccess(t: StockRes) {

                        if (t.status == StockApi.SUCCESS) {

                            val results: ArrayList<MenuManagementActivity.StockData> = ArrayList()

                            for (i in t.results!!.indices) {

                                val temp: MenuManagementActivity.StockData = MenuManagementActivity.StockData(
                                        t.results[i].stockCode,
                                        t.results[i].stockName,
                                        t.results[i].stockAmount
                                )
                                results.add(temp)
                            }

                            mView!!.setStock(results)
                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("stock", t.toString())
                    }
                }

        mInteractor!!.getStock(shopId, callback)
    }

    fun deleteMenu(shopId: String, menuName: String) {

        val callback: OnFinishApiListener<Status> =
                object: OnFinishApiListener<Status> {
                    override fun onSuccess(t: Status) {

                        if (t.status == MenuApi.SUCCESS) {


                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("menudelete", t.toString())
                    }

                }

        mInteractor!!.deleteMenu(shopId, menuName, callback)
    }

    fun updateMenu(shopId: String, menuName: String, menuNewName: String, menuPrice: Int, menuIngredients: String) {

        val callback: OnFinishApiListener<Status> =
                object: OnFinishApiListener<Status> {
                    override fun onSuccess(t: Status) {
                        if (t.status == MenuApi.SUCCESS) {

                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("menuupdate", t.toString())
                    }

                }

        mInteractor!!.updateMenu(shopId, menuName, menuNewName, menuPrice, menuIngredients, callback)
    }

    fun updateStock(req: StockModel) {

        val callback: OnFinishApiListener<Status> =
                object: OnFinishApiListener<Status> {
                    override fun onSuccess(t: Status) {
                        if (t.status == StockApi.SUCCESS) {

                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("stockUpdate", t.toString())
                    }

                }

        mInteractor!!.updateStock(req, callback)
    }

    fun deleteStock(shopId: String, stockCode: Int, stockName: String) {

        val callback: OnFinishApiListener<Status> =
                object: OnFinishApiListener<Status> {
                    override fun onSuccess(t: Status) {
                        if (t.status == StockApi.SUCCESS) {

                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("stockDelete", t.toString())
                    }

                }

        mInteractor!!.deleteStock(shopId, stockCode, stockName, callback)
    }
}