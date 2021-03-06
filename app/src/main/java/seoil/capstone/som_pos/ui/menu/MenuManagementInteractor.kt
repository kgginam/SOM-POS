package seoil.capstone.som_pos.ui.menu

import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.*

class MenuManagementInteractor: MenuManagementContract.Interactor {
    override fun getMenuInfo(shopId: String, onFinishApiListener: OnFinishApiListener<MenuRes>) {
        AppApiHelper.getInstance().getMenuInfo(shopId, onFinishApiListener)
    }

    override fun getStock(shopId: String, onFinishApiListener: OnFinishApiListener<StockRes>) {
        AppApiHelper.getInstance().getStock(shopId, onFinishApiListener)
    }

    override fun deleteMenu(menuCode: Int, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().deleteMenu(menuCode, onFinishApiListener)
    }

    override fun updateMenu(menuCode: Int, menuName: String, menuPrice: Int, menuIngredients: String, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().updateMenu(menuCode, menuName, menuPrice, menuIngredients, onFinishApiListener)
    }

    override fun deleteStock(shopId: String, stockCode: Int, stockName: String, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().deleteStock(shopId, stockCode, stockName, onFinishApiListener)
    }

    override fun updateStock(req: StockUpdateNameModel, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().updateStock(req, onFinishApiListener)
    }

    override fun insertStock(req: StockModel, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().insertStock(req, onFinishApiListener)
    }

    override fun insertMenu(shopId: String, req: MenuModel, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().insertMenu(shopId, req, onFinishApiListener)
    }

    override fun updateMenuIngredients(menuCode: Int, menuIngredients: String, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().updateMenuIngredients(menuCode, menuIngredients, onFinishApiListener)
    }


}