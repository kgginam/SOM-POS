package seoil.capstone.som_pos.ui.sell

import seoil.capstone.som_pos.base.BaseContract
import seoil.capstone.som_pos.data.model.DataModel
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.*

interface SellManagementContract {

    interface View: BaseContract.View {

        fun setMenuInfo(menuList: ArrayList<DataModel.MenuData>)
        fun initTotalPrice(countList: ArrayList<Int>)
        fun setStock(stockList: ArrayList<DataModel.StockData>)
        fun finishActivity(msg: String)
        fun setCurrentPoint(point: Int, userId: String)
    }

    interface Presenter: BaseContract.Presenter<View> {

        fun initTotalPrice(countList: ArrayList<Int>)
    }

    interface Interactor: BaseContract.Interactor {

        fun getMenuInfo(shopId: String, onFinishApiListener: OnFinishApiListener<MenuRes>)
        fun getStock(shopId: String, onFinishApiListener: OnFinishApiListener<StockRes>)
        fun pay(req: PaymentModel, onFinishApiListener: OnFinishApiListener<Status>)
        fun getCurrentPoint(userId: String, onFinishApiListener: OnFinishApiListener<GetCurrentRes>)
    }
}