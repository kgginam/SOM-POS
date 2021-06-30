package seoil.capstone.som_pos.ui.menu

import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.MenuRes
import seoil.capstone.som_pos.data.network.model.Status
import seoil.capstone.som_pos.data.network.model.StockModel
import seoil.capstone.som_pos.data.network.model.StockRes

class MenuManagementInteractor: MenuManagementContract.Interactor {
    override fun getMenuInfo(shopId: String, onFinishApiListener: OnFinishApiListener<MenuRes>) {
        AppApiHelper.getInstance().getMenuInfo(shopId, onFinishApiListener)
    }

    override fun getStock(shopId: String, onFinishApiListener: OnFinishApiListener<StockRes>) {
        AppApiHelper.getInstance().getStock(shopId, onFinishApiListener)
    }

    override fun deleteMenu(shopId: String, menuName: String, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().deleteMenu(shopId, menuName, onFinishApiListener)
    }

    override fun updateMenu(shopId: String, menuName: String, menuNewName: String, menuPrice: Int, menuIngredients: String, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().updateMenu(shopId, menuName, menuNewName, menuPrice, menuIngredients, onFinishApiListener)
    }

    override fun deleteStock(shopId: String, stockCode: Int, stockName: String, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().deleteStock(shopId, stockCode, stockName, onFinishApiListener)
    }

    override fun updateStock(req: StockModel, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().updateStock(req, onFinishApiListener)
    }


}