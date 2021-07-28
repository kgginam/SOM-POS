package seoil.capstone.som_pos.ui.sell

import seoil.capstone.som_pos.base.BaseContract
import seoil.capstone.som_pos.data.model.DataModel
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.MenuRes
import seoil.capstone.som_pos.data.network.model.StockRes

interface SellManagementContract {

    interface View: BaseContract.View {

        fun setMenuInfo(menuList: ArrayList<DataModel.MenuData>)
        fun initTotalPrice(countList: ArrayList<Int>)
        fun setStock(stockList: ArrayList<DataModel.StockData>)
    }

    interface Presenter: BaseContract.Presenter<View> {

        fun initTotalPrice(countList: ArrayList<Int>)
    }

    interface Interactor: BaseContract.Interactor {

        fun getMenuInfo(shopId: String, onFinishApiListener: OnFinishApiListener<MenuRes>)
        fun getStock(shopId: String, onFinishApiListener: OnFinishApiListener<StockRes>)
    }
}