package seoil.capstone.som_pos.ui.menu

import android.util.Log
import seoil.capstone.som_pos.data.model.DataModel
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.api.MenuApi
import seoil.capstone.som_pos.data.network.api.StockApi
import seoil.capstone.som_pos.data.network.model.*

class MenuManagementPresenter: MenuManagementContract.Presenter {


    private var mView: MenuManagementContract.View? = null
    private var mInteractor: MenuManagementInteractor? = null

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

    override fun getStock(): ArrayList<DataModel.StockData>? {
        return mView!!.getStock()
    }

    override fun getIngredients(position: Int): String? {
        return mView!!.getIngredients(position)
    }

    override fun createAlert(type: Int, position: Int) {
        mView!!.createAlert(type, position)
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

    fun getStock(shopId: String) {

        val callback: OnFinishApiListener<StockRes> =
                object: OnFinishApiListener<StockRes> {
                    override fun onSuccess(t: StockRes) {

                        Log.d("getStock", t.toString())
                        if (t.status == StockApi.SUCCESS) {

                            val results: ArrayList<DataModel.StockData> = ArrayList()

                            for (result: StockModel in t.results!!) {

                                results.add(
                                        DataModel.StockData(
                                            result.stockCode,
                                            result.stockName,
                                            result.stockAmount
                                        )
                                )
                            }

                            mView!!.setStock(results)
                        } else if (t.status == StockApi.ERROR_NONE_DATA) {

                            val results: ArrayList<DataModel.StockData> = ArrayList()
                            mView!!.setStock(results)
                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("stock", t.toString())
                    }
                }

        mInteractor!!.getStock(shopId, callback)
    }

    fun deleteMenu(menuCode: Int) {

        val callback: OnFinishApiListener<Status> =
                object: OnFinishApiListener<Status> {
                    override fun onSuccess(t: Status) {

                        if (t.status == MenuApi.SUCCESS) {

                            mView!!.initMenu()
                            mView!!.showDialog("메뉴가 삭제되었습니다.")
                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("menudelete", t.toString())
                    }

                }

        mInteractor!!.deleteMenu(menuCode, callback)
    }

    fun updateMenu(menuCode: Int, menuName: String, menuPrice: Int, menuIngredients: String) {

        val callback: OnFinishApiListener<Status> =
                object: OnFinishApiListener<Status> {
                    override fun onSuccess(t: Status) {
                        if (t.status == MenuApi.SUCCESS) {

                            mView!!.initMenu()
                            mView!!.showDialog("메뉴가 변경되었습니다.")
                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("menuupdate", t.toString())
                    }

                }

        mInteractor!!.updateMenu(menuCode, menuName, menuPrice, menuIngredients, callback)
    }

    fun updateStock(req: StockUpdateNameModel) {

        val callback: OnFinishApiListener<Status> =
                object: OnFinishApiListener<Status> {
                    override fun onSuccess(t: Status) {
                        if (t.status == StockApi.SUCCESS) {

                            mView!!.initStock()
                            mView!!.showDialog("재고가 변경되었습니다.")
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

                            mView!!.initStock()
                            mView!!.showDialog("재고가 삭제되었습니다.")
                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("stockDelete", t.toString())
                    }

                }

        mInteractor!!.deleteStock(shopId, stockCode, stockName, callback)
    }

    fun insertStock(req: StockModel) {

        val callback: OnFinishApiListener<Status> =
                object: OnFinishApiListener<Status> {
                    override fun onSuccess(t: Status) {

                        if (t.status == StockApi.SUCCESS) {

                            mView!!.initStock()
                            mView!!.showDialog("재고가 추가되었습니다.")
                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("stockInsert", t.toString())
                    }

                }

        mInteractor!!.insertStock(req, callback)
    }

    fun insertMenu(shopId: String, req: MenuModel) {

        val callback: OnFinishApiListener<Status> =
                object: OnFinishApiListener<Status> {
                    override fun onSuccess(t: Status) {

                        if (t.status == MenuApi.SUCCESS) {

                            mView!!.initMenu()
                            mView!!.showDialog("메뉴가 추가되었습니다.")
                        }
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.d("menuInsert", t.toString())
                    }

                }
        mInteractor!!.insertMenu(shopId, req, callback)
    }

    fun updateMenuIngredients(menuCode: Int, menuIngredients: String) {

        val callback: OnFinishApiListener<Status> =
                object: OnFinishApiListener<Status> {
                    override fun onSuccess(t: Status) {

                        if (t.status == MenuApi.SUCCESS) {

                            mView!!.initMenu()
                            mView!!.showDialog("메뉴의 재료가 변경되었습니다.")
                        }
                    }

                    override fun onFailure(t: Throwable?) {

                    }

                }
        mInteractor!!.updateMenuIngredients(menuCode, menuIngredients, callback)
    }
}