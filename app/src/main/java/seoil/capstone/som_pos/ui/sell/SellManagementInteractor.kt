package seoil.capstone.som_pos.ui.sell

import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.MenuRes
import seoil.capstone.som_pos.data.network.model.StockRes

class SellManagementInteractor: SellManagementContract.Interactor{

    override fun getMenuInfo(shopId: String, onFinishApiListener: OnFinishApiListener<MenuRes>) {
        AppApiHelper.getInstance().getMenuInfo(shopId, onFinishApiListener)
    }

    override fun getStock(shopId: String, onFinishApiListener: OnFinishApiListener<StockRes>) {
        AppApiHelper.getInstance().getStock(shopId, onFinishApiListener)
    }
}