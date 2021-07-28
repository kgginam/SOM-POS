package seoil.capstone.som_pos.ui.sell

import android.util.Log
import seoil.capstone.som_pos.data.model.DataModel
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.api.MenuApi
import seoil.capstone.som_pos.data.network.api.StockApi
import seoil.capstone.som_pos.data.network.model.MenuRes
import seoil.capstone.som_pos.data.network.model.StockModel
import seoil.capstone.som_pos.data.network.model.StockRes

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

    fun isTextSet(str: String?): Boolean {
        if (str == null || str == "" || str.isEmpty()) {

            return false
        }
        return true
    }

    fun getMenuMaxCount(ingredients: String, stockData: ArrayList<DataModel.StockData>?): Int {

        if(!isTextSet(ingredients)) {

            return -1
        } else {

            var min: Int = 10000

            val firstSplit: List<String> = ingredients.split(",")

            for (i in firstSplit.indices) {

                val secondSplit: List<String> = firstSplit[i].split(":")

                val chk = stockData!!.find { it.stockCode == secondSplit[0].toInt() }

                if (chk == null) {

                    min = 0
                    break
                } else {

                    if (min > chk.stockAmount!! / secondSplit[1].toInt()) {

                        min = chk.stockAmount!! / secondSplit[1].toInt()
                    }
                }
            }

            return min
        }
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
                        } else if (t.status ==StockApi.ERROR_NONE_DATA) {

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
}