package seoil.capstone.som_pos.ui.menu

import seoil.capstone.som_pos.base.BaseContract
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.*

interface MenuManagementContract {

    interface View: BaseContract.View {

        fun setMenuInfo(menuInfo: ArrayList<MenuManagementActivity.MenuData>)
        fun setStock(stockData: ArrayList<MenuManagementActivity.StockData>)
        fun initMenu()
        fun initStock()
    }

    interface Presenter: BaseContract.Presenter<View> {

    }

    interface Interactor: BaseContract.Interactor {

        fun getMenuInfo(shopId: String, onFinishApiListener: OnFinishApiListener<MenuRes>)
        fun getStock(shopId: String, onFinishApiListener: OnFinishApiListener<StockRes>)
        fun deleteMenu(shopId: String, menuName: String, onFinishApiListener: OnFinishApiListener<Status>)
        fun updateMenu(shopId: String, menuName: String, menuNewName: String,
                       menuPrice: Int, menuIngredients: String, onFinishApiListener: OnFinishApiListener<Status>)
        fun deleteStock(shopId: String, stockCode: Int, stockName: String, onFinishApiListener: OnFinishApiListener<Status>)
        fun updateStock(req: StockModel, onFinishApiListener: OnFinishApiListener<Status>)
        fun insertStock(req: StockModel, onFinishApiListener: OnFinishApiListener<Status>)
        fun insertMenu(shopId: String, req: MenuModel, onFinishApiListener: OnFinishApiListener<Status>)
    }
}