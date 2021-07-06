package seoil.capstone.som_pos.ui.menu

import seoil.capstone.som_pos.base.BaseContract
import seoil.capstone.som_pos.data.model.DataModel
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.*

interface MenuManagementContract {

    interface View: BaseContract.View {

        fun setMenuInfo(menuInfo: ArrayList<DataModel.MenuData>)
        fun setStock(stockData: ArrayList<DataModel.StockData>)
        fun initMenu()
        fun initStock()
        fun getStock(): ArrayList<DataModel.StockData>?
        fun getIngredients(position: Int): String?
        fun createAlert(type: Int, position: Int)
    }

    interface Presenter: BaseContract.Presenter<View> {

        fun getStock(): ArrayList<DataModel.StockData>?
        fun getIngredients(position: Int): String?
        fun createAlert(type: Int, position: Int)
    }

    interface Interactor: BaseContract.Interactor {

        fun getMenuInfo(shopId: String, onFinishApiListener: OnFinishApiListener<MenuRes>)
        fun getStock(shopId: String, onFinishApiListener: OnFinishApiListener<StockRes>)
        fun deleteMenu(menuCode: Int, onFinishApiListener: OnFinishApiListener<Status>)
        fun updateMenu(menuCode: Int, menuName: String, menuPrice: Int, menuIngredients: String, onFinishApiListener: OnFinishApiListener<Status>)
        fun deleteStock(shopId: String, stockCode: Int, stockName: String, onFinishApiListener: OnFinishApiListener<Status>)
        fun updateStock(req: StockUpdateNameModel, onFinishApiListener: OnFinishApiListener<Status>)
        fun insertStock(req: StockModel, onFinishApiListener: OnFinishApiListener<Status>)
        fun insertMenu(shopId: String, req: MenuModel, onFinishApiListener: OnFinishApiListener<Status>)
        fun updateMenuIngredients(menuCode: Int, menuIngredients: String, onFinishApiListener: OnFinishApiListener<Status>)
    }
}